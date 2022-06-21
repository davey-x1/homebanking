Vue.createApp({
    data() {
        return {
            template: 'login'
        }
    },
    methods: {
      openModal(option){
        document.getElementById('main__modal').style.display = 'block';
        this.template = option;
      },
      loginMethod: function(e) {
          e.preventDefault();
          let eventTargets = e.target.elements;
          let mail = eventTargets.emailL.value;
          let pwd = eventTargets.pwdL.value;

          axios.post('/api/login',
          "email=" + mail + "&password=" + pwd,
          {headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(response => {alert("Successfully logged in"); window.location.href = "accounts.html";})
          .catch(response => alert("Email or password incorrect"))

      },
      signupMethod: function(e) {
          e.preventDefault();
          let eventTargets = e.target.elements;
          let fName = eventTargets.fNameS.value;
          let lName = eventTargets.lNameS.value;
          let mail = eventTargets.emailS.value;
          let pwd = eventTargets.pwdS.value;

          axios.post('/api/clients',
          "firstName="+ fName +"&lastName="+ lName + "&email=" + mail + "&password=" + pwd,
          {headers:{'content-type':'application/x-www-form-urlencoded'}})
          .then(response =>
            {
              alert("You have successfully signed up, you now can proceed to log-in");

            })
          .catch(response => alert("Error"))
      }
    }
}).mount('#app')
