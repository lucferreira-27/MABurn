(query) => {
    var episodioServers = document.querySelectorAll(query);
    var servidor = episodioServers[0].dataset.video;
    window.location = servidor;
    return document.querySelector("video").src;

}
