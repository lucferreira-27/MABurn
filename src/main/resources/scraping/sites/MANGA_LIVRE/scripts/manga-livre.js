
let values = []
async function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }
    if (!target) {
        maburn.screenshot()
        maburn.waitElement('.list-of-chapters li a').then(() => {
            collectTitles().then(values =>{
                maburn.send("RESULT", JSON.stringify(values))
                maburn.exit()
            })

        })

    }
    else {
        maburn.waitElement('.manga-image').then(() => {
            values.push({ name: target, url: collect(), type: 'manga' });
            maburn.send("RESULT", JSON.stringify(values))
            maburn.exit()
        })
    }

    function getAndNext(position, pages) {
        pages.push({ page: position, image: document.querySelector('.manga-image img').src })
        document.querySelector('.page-next').click()
    }
    function collect() {
        var total = parseInt(document.querySelector('[reader-total-pages]').textContent)
        console.log(total)
        let pages = []
        for (var i = 1; i <= total; i++) {
            getAndNext(i, pages)

        }
        return pages
    }


    async function collectTitles(){
        

        async function getAllUrls(urls) {
            try {
                var data = await Promise.all(
                    urls.map(
                        url =>
                            fetch(url,{
                                "headers": {
                                    "accept": "application/json, text/javascript, */*; q=0.01",
                                    "accept-language": "pt-BR,pt;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
                                    "sec-ch-ua": "\" Not;A Brand\";v=\"99\", \"Microsoft Edge\";v=\"97\", \"Chromium\";v=\"97\"",
                                    "sec-ch-ua-mobile": "?0",
                                    "sec-ch-ua-platform": "\"Windows\"",
                                    "sec-fetch-dest": "empty",
                                    "sec-fetch-mode": "cors",
                                    "sec-fetch-site": "same-origin",
                                    "x-requested-with": "XMLHttpRequest"
                                },
                                "referrerPolicy": "strict-origin-when-cross-origin",
                                "body": null,
                                "method": "GET",
                                "mode": "cors",
                                "credentials": "include"
                            }).then(
                                (response) => response.json()
                            )));
        
                return (data)
        
            } catch (error) {
                console.log(error)
        
                throw (error)
            }
        }


        function collectJSONLinks(){
            let links = []  
          let txtTotal = document.querySelector('#chapter-list div.color-brown > h2 > span').innerText
            let total = Math.ceil(parseInt(txtTotal) / 30)
            
            let idSerie = performance.getEntriesByType("resource")
            .map(r => r.name)
            .filter(r => r.match(/id_serie=\d+/g))
            .map(r => r.match(/id_serie=\d+/g))[0][0]
        
        
            for(let i = 1; i <= total; i++){
                links.push(`https://mangalivre.net/series/chapters_list.json?page=${i}&${idSerie}`)
            }
            return links;
        }
        
        
        let chaptersChunks =  await getAllUrls(collectJSONLinks())
        let chapters = []
        chaptersChunks.map(c => c.chapters).forEach(array =>{
            array.forEach(el =>{
                chapters.push(el)
            })
        })
        var response = chapters.map(c => {
            var chapter = {name :  "[" + c.number + "] " + c.chapter_name, url : "https://mangalivre.net" + c.releases[Object.keys(c.releases)[0]].link}
            return chapter
        })
        return response
     
  
}
       


}
