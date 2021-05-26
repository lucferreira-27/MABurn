(query) => {
    let chapters = document.querySelectorAll(query)
    let txtChapters = []
    chapters.forEach(e =>  txtChapters.push(e.href))
    txtChapters.reverse()
    return txtChapters;
}