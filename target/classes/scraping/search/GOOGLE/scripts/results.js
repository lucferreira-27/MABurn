(query) => {
    var results = document.querySelectorAll('.yuRUbf a')
    var links = []
    results.forEach(r => {
        if (r.classList.length == 0)
            links.push(r.href)
    })

    
    return links;
}
