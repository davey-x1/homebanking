Vue.createApp({
    data() {
        return {
            currentSession: {},
            accountTypeChosen: 'PLATINUM',
            existingLoans: {},
            currentLoan: {},
            currentLoanActive: 'NO'
        }
    },
    created() {
        axios.get('../api/clients/current')
            .then(response => response)
            .then(data => {
                this.currentSession = data.data;
                console.log(data.data);
            });
        axios.get('../loans')
                .then(response => response)
                .then(data => {
                    this.existingLoans = data.data;
                    console.log(data.data);
                });
    },
    methods: {
      refreshSession(){
        axios.get('../api/clients/current')
            .then(response => response)
            .then(data => {
                this.currentSession = data.data;
                console.log(data.data);
            });
      },
        // --------------------------------------------------
        addCard(){
          Swal.fire({
            title: 'New Card',
            html: `<select id = "select-color" class = "swal2-input">
              <option value = "GOLD">GOLD</option>
              <option value = "PLATINUM">PLATINUM</option>
              <option value = "TITANIUM">TITANIUM</option>
            </select>
            <select id = "select-type" class = "swal2-input">
              <option value = "CREDIT">CREDIT</option>
              <option value = "DEBIT">DEBIT</option>
            </select>`,
            confirmButtonText: 'Create card',
            focusConfirm: false,
            preConfirm: () => {
              const login = Swal.getPopup().querySelector('#select-color').value
              const password = Swal.getPopup().querySelector('#select-type').value
              if (!login || !password) {
                Swal.showValidationMessage(`Please choose valid options`)
              }
              return { login: login, password: password }
            }
          }).then((result) => {

            let color = result.value.login;
            let type = result.value.password;
            if(this.checkCards(type)){
              let newCard = {};
              let clientCreated = JSON.parse(JSON.stringify(this.currentSession));
              newCard.cardType = type;
              newCard.cardColor = color;
              newCard.owner = clientCreated;

              axios({
                  method: 'post',
                  url: '/card',
                  data: newCard
              })
                  .then((response) => {
                      Swal.fire(
                        "Success!",
                        "Your card has been created",
                        'success'
                      );
                      this.refreshSession();
                  })
                  .catch(function (error) {
                      console.log(error);
                  });
            } else {
              Swal.fire(
                "Error!",
                "You can only have a maximum of 3 cards of that type",
                'error'
              );
            }
        })
      },
        checkCards(type){
          let cards = this.currentSession.cards;
          let cardType = 0;
          for(let i = 0; i < cards.length; i++){
            if(cards[i].cardType == type){
              cardType++;
            }
          }

          if(cardType == 3){
            return false;
          } else {
            return true;
          }
        },
        changeColor(color){
          this.accountTypeChosen = color;
          if(color == "GOLD"){
            $('.card__part').addClass('bg--gold');
            $('.card__part').removeClass('bg--platinum');
            $('.card__part').removeClass('bg--titanium');
          } else if(color == "PLATINUM"){
            $('.card__part').addClass('bg--platinum');
            $('.card__part').removeClass('bg--gold');
            $('.card__part').removeClass('bg--titanium');
          } else {
            $('.card__part').addClass('bg--titanium');
            $('.card__part').removeClass('bg--platinum');
            $('.card__part').removeClass('bg--gold');
          }
        },
        changeActiveLoan(loan){
          this.currentLoan = loan;
          this.currentLoanActive = 'YES';
        },
        createLoan(e){
          e.preventDefault();
          let formUsed = e.target;
          let payment = formUsed.selectpayment.value;
          let amount = formUsed.amount.value;
          if(amount > this.currentLoan.maxAmountOfLoan){
            alert("You can't ask for more money than we allow");
          }
          let loanEntityy = JSON.stringify(this.currentLoan);
          let clientEntity = JSON.stringify(this.currentSession);
          let clientLoan = {};
          clientLoan.amountOfLoan = amount;
          clientLoan.ownerOfLoan = clientEntity;
          clientLoan.loanEntity = loanEntityy;
          clientLoan.payments = payment;
          console.log(loanEntityy);
          axios({
              method: 'post',
              url: '../loans/add/loan',
              data: clientLoan
          })
              .then((response) => {
                  Swal.fire(
                    "Success!",
                    "Your loan has been created",
                    'success'
                  );
                  this.refreshSession();
              })
              .catch(function (error) {
                  console.log(error);
              });
        },
        // ----------------------------------------
        changeTemplate(template){
            this.actualTemplate = template;
        }
    }
}).mount('#app')
