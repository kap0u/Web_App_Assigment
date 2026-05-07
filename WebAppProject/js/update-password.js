document.getElementById("updateForm").addEventListener("submit", function (e) {
  const form = e.target;
  const oldpass = form.oldpass.value;
  const newpass1 = form.newpass1.value;
  const newpass2 = form.newpass2.value;
  const msgDiv = document.getElementById("message");

  if (newpass1 !== newpass2) {
    msgDiv.innerText = "Τα νέα passwords δεν ταιριάζουν.";
    e.preventDefault();
    return;
  }
  if (oldpass === newpass1) {
    msgDiv.innerText = "Το νέο password πρέπει να είναι διαφορετικό από το παλιό.";
    e.preventDefault();
    return;
  }
  if (!/^[a-zA-Z0-9]+$/.test(newpass1) || newpass1.length < 6) {
    msgDiv.innerText = "Το νέο password πρέπει να περιέχει μόνο γράμματα/αριθμούς και να έχει πάνω από 5 χαρακτήρες.";
    e.preventDefault();
    return;
  }
});