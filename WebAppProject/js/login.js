document.getElementById("loginForm").addEventListener("submit", function (e) {
  const username = e.target.username.value;
  const password = e.target.password.value;
  const msgDiv = document.getElementById("login-message");

  if (!username || !password) {
    msgDiv.innerText = "Συμπλήρωσε και τα δύο πεδία.";
    e.preventDefault();
  }
});