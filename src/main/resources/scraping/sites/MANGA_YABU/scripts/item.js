(query) => {
    var page = document.querySelectorAll(query);
    var txtPages = [];
    page.forEach((p) =>{

        txtPages.push(p.src)
    })
    
    return txtPages;
}
