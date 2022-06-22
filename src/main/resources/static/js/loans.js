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
          let formUsed = e.target;
          let payment = formUsed.selectpaymentoption.value;
          let account = formUsed.selectaccountoption.value;
          let amount = formUsed.amount.value;
          if(payment == "" || amount == "" || account == ""){
            Swal.fire(
              "Error!",
              "Please fill every input",
              'error'
            );
          } else {
            let interes = parseInt(amount) * 0.2;
            let totalPayment = parseInt(amount) + interes;
            let cuotas = parseInt(totalPayment) / parseInt(payment);
            console.log(cuotas);
            Swal.fire({
            title: 'Are you sure?',
            text: "You'll have to pay " + payment + " payments of " + cuotas + " each",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, give me all your money!'
          }).then( (result) => {
            if (result.isConfirmed) {
              if (this.checkType() == true) {
                Swal.fire(
                  "Can't get a loan!",
                  "You already have one of these kind of loans, please pay it first",
                  'error'
                );
              } else {
                if(parseInt(amount) > parseInt(this.currentLoan.maxAmount)){
                  Swal.fire(
                    "Error!",
                    "You can't ask for more money than we allow",
                    'error'
                  );
                } else {
                  let loanEntityy = {};
                  let clientEntity = {};
                  clientEntity.id = this.currentSession.id;
                  let clientLoan = {};
                  let accountEntity = {};
                  // -------------------------------------- //
                  loanEntityy.id = this.currentLoan.id;
                  accountEntity.id = account;
                  // -------------------------------------- //
                  // -------------------------------------- //
                  clientLoan.amountOfLoan = parseInt(amount);
                  clientLoan.ownerOfLoan = clientEntity;
                  clientLoan.loanEntity = loanEntityy;
                  clientLoan.paymentOfLoans = payment;
                  clientLoan.accountOfLoan = accountEntity;
                  // -------------------------------------- //
                  // -------------------------------------- //
                  axios({
                      method: 'post',
                      url: '../cloans',
                      data: clientLoan
                  }).then((response) => {
                      Swal.fire(
                        "Success!",
                        "Your loan has been created",
                        'success'
                      );
                  }).catch((error) => {
                      Swal.fire(
                        "Error!",
                        "Please fill every input correctly",
                        'error'
                      );
                  });

                }
              }
            }
          });
          }
      },
        // ----------------------------------------
        changeTemplate(template){
            this.actualTemplate = template;
        },
        checkType(){
          let array = this.currentSession.loansOwned;
          let type = this.currentLoan.name;
          for(let i = 0; i < array.length; i++){
            if(array[i].nameOfLoan == type){
              return true;
            }
          }
          return false;
        },
        logout: function(){
          axios.post('../api/logout').then(response => console.log('signed out!!!'));
          window.location.href = '../pages/login.html';
        }
    }
}).mount('#app')
