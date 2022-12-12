var data = { key: "5efe467fa36d4e0c7e2a806bcb1acf68", q: document.getElementById("URL").href };

fetch("https://api.linkpreview.net", {
  method: "POST",
  mode: "cors",
  body: JSON.stringify(data)
})
  .then((res) => res.json())
  .then((response) => {
    document.getElementById("mytitle").innerHTML = response.title;
    document.getElementById("mydescription").innerHTML = response.description;
    document.getElementById("myimage").src = response.image;
    document.getElementById("myurl").innerHTML = response.url;
  });
