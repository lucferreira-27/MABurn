
    let values = []


function execute(target){
   
    if(maburn.active){
        console.log("Maburn already actived!")
        return
    }

   maburn.on()

   if(!target){
    maburn.screenshot()
   }

   maburn.waitElement('.tabs').then((value) =>{
       
         var server  = document.querySelector('.tabs-anime  li:nth-child(2)') == undefined  ? "" : 2;
         var selector = ".tab_content-pag-bnt" + server + " > div > a";
         maburn.send("GOTO",document.querySelector(selector).href);
     })
 
    maburn.waitElement('.drive-entry').then(async () =>{
         
        
         console.log("Target: " + target)
         if(!target){
            getAllNames() 
         }else{
            values = [{name : "",  url : ""}]
            values[0].name = target
            await getUrl(target)
         }         
         exit()

        
    })
    

    function exit(){
        
        maburn.send("RESULT",JSON.stringify(values))
        maburn.exit()
    }

 
 }

 function getAllNames(){
       
    document.querySelectorAll('span.entry-name').forEach((e)=>{
        values.push({name : e.textContent,  url : window.location.href})
    })
    return values;
}


async function getUrl(name) {

    var elements = document.querySelectorAll('.drive-entry')
    var index = 0;
    for (let el = 0; el < elements.length; el++) {
         if(elements[el].textContent.includes(name)){
            index = el + 1
         }
    }



        await Promise.resolve(new Promise(async (resolve) => {

            maburn.send("CLICK", `.drive-entry:nth-child(${index})`);
            await maburn.sleep(1000);
            resolve(maburn.waitElement('source'));


        }).then(async () => {

            var source = document.querySelector('source');
            var result = source.src;

            values[0].url = result;
            maburn.send("CLICK", `.back-button`);

            await maburn.sleep(1000);

        }));

  
}
async function getAllUrls(elements) {
    for (let el = 0; el < elements.length; el++) {
        var child = el + 1;

        await Promise.resolve(new Promise(async (resolve) => {

            maburn.send("CLICK", `.drive-entry:nth-child(${child})`);
            await maburn.sleep(1000);
            resolve(maburn.waitElement('source'));


        }).then(async () => {

            var source = document.querySelector('source');
            var result = source.src;

            values[child - 1].url = result;
            maburn.send("CLICK", `.back-button`);

            await maburn.sleep(1000);

        }));

    }

    async function searchFor(search){

        var d = await fetch("https://saikoanimes.net/wp-json/facetwp/v1/refresh", {
            "headers": {
              "content-type": "application/x-www-form-urlencoded; charset=UTF-8",
              "x-requested-with": "XMLHttpRequest"
            },
            "body": `action=facetwp_refresh&data%5Bfacets%5D=%7B%22pesquisa%22%3A%22${search}%22%2C%22tipo%22%3A%5B%5D%2C%22udio%22%3A%5B%5D%2C%22verso%22%3A%5B%5D%2C%22status%22%3A%5B%5D%2C%22gnero%22%3A%5B%5D%7D&data%5Bhttp_params%5D%5Bget%5D%5Bfwp_pesquisa%5D=Dragon%2520Ball%2520Z&data%5Bhttp_params%5D%5Buri%5D=multimidia&data%5Bhttp_params%5D%5Burl_vars%5D%5Btipo%5D%5B%5D=anime&data%5Btemplate%5D=lista&data%5Bextras%5D%5Bsort%5D=default&data%5Bsoft_refresh%5D=0&data%5Bis_bfcache%5D=1&data%5Bfirst_load%5D=0&data%5Bpaged%5D=1`,
            "method": "POST",
            "mode": "cors",
            "credentials": "include"
          }).then(value => value.json()).then(html => {
             var innerHtml = html.template
                      var parser = new DOMParser();
              var doc = parser.parseFromString(innerHtml, 'text/html');
               return doc
          });
          
          
          let results = []
          d.querySelectorAll("#content").forEach(content =>{
              var image = content.querySelector("img").src
              var link = content.querySelector("a").href
               results.push({link : link, image : image})
          })
          maburn.send("RESULT", results)
    }

}

