
    let values = []
    async  function execute(target){
        if (maburn.active) {
            console.log("Maburn already actived!")
            return
        }
        if(!target)
            maburn.screenshot()
          
            maburn.waitElement('.videos-tabs').then( async () =>{
              
              maburn.send('CLICK','#full')
              maburn.waitElement('.ep').then(() =>{
                var quality =  document.querySelector('#full') ? "#full" : "#hd"
                  
               
                  var elements= document.querySelectorAll(`${quality} .ep`)
                  elements.forEach((el) =>{
                      var name = el.querySelector("span.nome-ep").textContent;
                      var url =  el.querySelector('[title="odrive"]').href
                      values.push({name : name, url :url})
                  })
                  maburn.send("RESULT", JSON.stringify(values))
                  maburn.exit()
  
              })
          })
  
          maburn.waitElement('#token_id').then(() =>{
              var token = document.querySelector('#token_id').value
              values.push({name : target, url : token})
              maburn.send("RESULT", JSON.stringify(values))
              maburn.exit()
  
          })
      }