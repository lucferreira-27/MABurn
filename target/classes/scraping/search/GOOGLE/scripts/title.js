(query) => {
    let episodios = document.querySelectorAll(query)
    let txtEpisodios = []
    episodios.forEach(e => {
        if (e.classList.length == 0) {
            txtEpisodios.push(e.href)
        }
    })
    return txtEpisodios;
}