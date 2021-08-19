



let values = []
function execute(target) {
    if (maburn.active) {
        console.log("Maburn already actived!")
        return
    }
    if (!target){
           maburn.screenshot()
    }   
    if(!target)
        maburn.waitElement('.chapter-list').then(()=>{
         
            document.querySelectorAll('.chapter-list .row a').forEach((e) =>{
                var name = e.title;
                var url = e.href
                values.push({name : name, url: url})
            })
            maburn.send("RESULT", JSON.stringify(values))
            maburn.exit()
        })
    else{
        maburn.waitElement('#page_select').then(()=>{
            values.push({name: target, url: collect(), type: 'manga' });
            maburn.send("RESULT", JSON.stringify(values))
            maburn.exit()
        })
    }
    function collect() {
        var pages = []
        document.querySelectorAll('#page_select option').forEach((p, index) =>{
            pages.push({page : index + 1, image : p.value})
        })
        return pages
    }



}



