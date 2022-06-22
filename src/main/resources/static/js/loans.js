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
                .then(response => {
                  this.existingLoans = response.data;
                  console.log(response.data);
                })
    },
    methods: {
      refreshSession(){
        axios.get('../api/clients/current')
            .then(response => response)
            .then(data => {
                this.currentSession = data.data;
            });
      },
        // --------------------------------------------------
        changeActiveLoan(loan){
          this.currentLoan = loan;
          this.currentLoanActive = 'YES';
        },
        createLoan(e){
          e.preventDefault();
          console.log(this.existingLoans);
          let formUsed = e.target;
          let payment = formUsed.selectpaymentoption.value;
          let amount = formUsed.amount.value;
          if(amount > this.currentLoan.maxAmountOfLoan){
            alert("You can't ask for more money than we allow");
          }
          let loanEntityy = {};
          let clientEntity = {};
          clientEntity.id = this.currentSession.id;
          let clientLoan = {};
          // -------------------------------------- //
          loanEntityy.id = this.currentLoan.id;
          // -------------------------------------- //
          // -------------------------------------- //
          clientLoan.amountOfLoan = parseInt(amount);
          clientLoan.ownerOfLoan = clientEntity;
          clientLoan.loanEntity = loanEntityy;
          clientLoan.paymentOfLoans = payment;
          // -------------------------------------- //
          // -------------------------------------- //
          axios({
              method: 'post',
              url: '../cloans',
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
                  Swal.fire(
                    "Error!",
                    "Please fill every input correctly",
                    'error'
                  )
              });
        },
        // ----------------------------------------
        changeTemplate(template){
            this.actualTemplate = template;
        }
    }
}).mount('#app')
