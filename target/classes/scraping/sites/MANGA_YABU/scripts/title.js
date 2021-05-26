(query) => {
    var chapters = document.querySelectorAll(query);
    var txtChapters = []
    chapters.forEach((c) => {
    
         txtChapters.push(c.href)
    })
    txtChapters.reverse()
    return txtChapters;
}