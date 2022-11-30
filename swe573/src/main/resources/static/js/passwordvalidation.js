function PassCheck() {
  var password = document.getElementById('pswd1');
  var vpassword = document.getElementById('pswd2');

  if (password.value != vpassword.value) {
    document.getElementById("button1").disabled = true;
  }
  else {
    document.getElementById("button1").disabled = false;
  }
}