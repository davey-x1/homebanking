Vue.createApp({
    data() {
        return {
            currentSession: {},
            usedAccount: {}
        }
    },
    created() {
        axios.get('../api/clients/current')
            .then(response => response)
            .then(data => {
                this.currentSession = data.data;
                console.log(data.data);
            });
    },
    methods: {
        addClient(submitEvent){
            let formArray = submitEvent.target.elements;
            let email = formArray.inputEmail.value;
            let first_name = formArray.firstName_client.value;
            let last_name = formArray.lastName_client.value;
            let client = {};
            client.email = email;
            client.firstName = first_name;
            client.lastName = last_name;
            axios({
                method: 'post',
                url: '/client',
                data: client
            })
                .then(function (response) {
                    console.log(response);

                })
                .catch(function (error) {
                    console.log(error);
                });
            this.getClients();
        },
        getClients(){
            document.location.reload(true);
        },
        deleteClient(client){
            axios.delete('/client/' + client.id)
                .then((response) => console.log(response));
            this.getClients();
        },
        openModal(client){
            let modal = document.getElementById("myModal");
            let idInput = document.getElementById("modifyId");
            let nameInput = document.getElementById("modifyName");
            let lastInput = document.getElementById("modifyLast");
            let email = document.getElementById("modifyEmail");
            idInput.value = client.id;
            nameInput.value = client.firstName;
            lastInput.value = client.lastName;
            email.value = client.email;
            modal.style.display = "block";
        },
        closeModal(){
            let modal = document.getElementById("myModal");
            modal.style.display = "none ";
        },
        modifyClient(formEvent){
            let formArray = formEvent.target.elements;
            let email = formArray.modifyEmail.value;
            let first_name = formArray.modifyName.value;
            let last_name = formArray.modifyLast.value;
            let id = formArray.modifyId.value;
            let client = {};
            client.id = id;
            client.email = email;
            client.firstName = first_name;
            client.lastName = last_name;
            axios({
                method: 'post',
                url: '/client',
                data: client
            })
                .then(function (response) {
                    console.log(response);

                })
                .catch(function (error) {
                    console.log(error);
                });
            this.getClients();
        },
        // --------------------------------------------------
        addAccount() {
          this.refreshSession();
          if(this.currentSession.accountsOwned.length === 3){
            Swal.fire(
              "We couldn't create a new account",
              "You can't have more than 3 accounts with us",
              'error'
            );
            this.refreshSession();
          } else {
            Swal.fire({
              title: 'Which type of account you want to create?',
              showDenyButton: true,
              showCancelButton: true,
              confirmButtonText: 'Checking',
              denyButtonText: 'Savings',
            }).then(result => {
              if (result.isConfirmed) {
                this.refreshSession();
                let accountCreated = {};
                let clientCreated = {};
                clientCreated.id = this.currentSession.id;
                // -----------------------------------
                accountCreated.accountOwner = clientCreated;
                accountCreated.accountType = "CHECKING";
                // -----------------------------------
                axios({
                    method: 'post',
                    url: '/account/create',
                    data: accountCreated
                })
                    .then((response) => {
                        console.log(response);
                        Swal.fire(
                          "Success!",
                          "Your account has been created",
                          'success'
                        );
                        this.refreshSession();
                    })
                    .catch((error) => {
                        console.log(error);
                    });
                Swal.fire('Created!', '', 'success');
              } else if (result.isDenied) {
                  this.refreshSession();
                  let clientCreated = {};
                  clientCreated.id = this.currentSession.id;
                  let accountCreated = {
                    accountOwner: clientCreated,
                    accountType: "SAVING"
                  };

                  // -----------------------------------
                  //accountCreated.accountOwner = clientCreated;
                  //accountCreated.accountType = "SAVINGS";
                  // -----------------------------------
                  axios({
                      method: 'post',
                      url: '/account/create',
                      data: accountCreated
                  })
                      .then((response) => {
                          Swal.fire(
                            "Success!",
                            "Your account has been created",
                            'success'
                          );
                          this.refreshSession();
                      })
                      .catch((error) => {
                          console.log(error);
                      });
                Swal.fire('Created!', '', 'success');
              }
            })
          }
          this.refreshSession();
        },
        deleteAccount(account){
          this.refreshSession();
          Swal.fire({
          title: 'Are you sure?',
          text: "You won't be able to revert this!",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (result.isConfirmed) {
            if(this.currentSession.accountsOwned.length == 1){
              Swal.fire(
                "You can't delete this account",
                "You need atleast one account to be with us",
                'error'
              );
            } else {
              axios.delete('/account/' + account.id)
              .then((response) => Swal.fire(
                'Deleted!',
                'Your account has been successfully deleted',
                'success'
              ));
              this.refreshSession();
            }

          }
          this.refreshSession();
        });
        },
        filterTransaction(event){
            let account_id = event.target.value;
            if(account_id == -1){

            } else {
            axios.get('/account/' + account_id)
            .then((response) => response)
            .then(result => {
                this.transactionData = result.data.transactionEntities;
                this.accountChosen = result.data.numberOfAccount;
                });
            }
        },

        createTransaction(){
          console.log(this.usedAccount);
          if(JSON.stringify(this.usedAccount) == JSON.stringify({})){
            Swal.fire(
              'Error!',
              'You have to choose an account in order to create a transaction',
              'error'
            )
          } else {
            Swal.fire({
              title: 'Login Form',
              html: `<input type="text" id="cuentaDestino" class="swal2-input" placeholder="Cuenta de Destino">
              <input type="text" id="description" class="swal2-input" placeholder="Descripcion">
              <input type="number" id="monto" class="swal2-input" placeholder="Monto">`,
              confirmButtonText: 'Sign in',
              focusConfirm: false,
              preConfirm: () => {
                const cuentaDestino = Swal.getPopup().querySelector('#cuentaDestino').value
                const description = Swal.getPopup().querySelector('#description').value
                const monto = Swal.getPopup().querySelector('#monto').value
                if (!cuentaDestino || !description || !monto) {
                  Swal.showValidationMessage(`Please fill all the fields`)
                }
                return { cuentaDestino: cuentaDestino, description: description, monto: monto }
              }
            }).then((result) => {
              let cuentaOrigen = this.usedAccount.numberOfAccount;
              let cuentaDestino = result.value.cuentaDestino;
              let description = result.value.description;
              let monto = result.value.monto;
              axios.post('../transactions/create',
              "monto=" + monto + "&description=" + description + "&cuentaOrigen=" + cuentaOrigen + "&cuentaDestino=" + cuentaDestino,
              {headers:{'content-type':'application/x-www-form-urlencoded'}})
              .then((response) => {
                Swal.fire(
                "Success!",
                "Your money has been sent",
                'success'
              );
            })
              .catch((response) => {
                Swal.fire(
                  "Error!",
                  response,
                  'error'
                );
          })
              /*Swal.fire(`
                Login: ${result.value.login}
                Password: ${result.value.password}
              `.trim())*/
            })
          }
        },
        refreshSession(){
          axios.get('../api/clients/current')
              .then(response => response)
              .then(data => {
                  this.currentSession = data.data;
                  console.log(data.data);
              });
        },
        // ----------------------------------------
        changeTemplate(template){
            this.actualTemplate = template;
        },
        logout: function(){
          axios.post('../api/logout').then(response => console.log('signed out!!!'));
          window.location.href = '../pages/login.html';
        }
    }
}).mount('#app')
