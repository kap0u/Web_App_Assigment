function sendTopic() {
  const name = document.getElementById("topicName").value;
  const desc = document.getElementById("topicDesc").value;

  fetch("new-topic", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name: name, description: desc })
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("result").innerText = data.message;
    });
}