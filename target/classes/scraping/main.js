class Maburn {


    constructor() {
        console.log("Maburn created!")
        this.active = false
    }

    createApp() {
        var app = document.createElement('app')
        app.id = 'maburn'
        document.children[0].appendChild(app)
    }


    createMessage(id, content) {

        var message = document.createElement("message")
        message.id = "MABURN_" + id
        message.textContent = content
        document.querySelector('#maburn').appendChild(message)
    }

    on() {
        this.send("ON", true)
        this.active = true;
    }

    exit() {

        this.send("ON", false)
        this.active = false;
    }

    sleep(milliseconds) {
        return new Promise(resolve => setTimeout(resolve, milliseconds))
    }
   async doIt(execution,milliseconds) {
        return  await new Promise(execution)
    }
    screenshot() {
        this.send("SCREENSHOT", true)
    }

    send(type, msg) {
        fetch('http://localhost:8001/end', {method: 'POST', body: `${type}('${msg}')`})
    }


    waitElement(selector) {

        return checkElement(selector)

        function checkElement(selector) {
            if (document.querySelector(selector) === null) {
                return rafAsync().then(() => checkElement(selector));
            } else {
                return Promise.resolve(document.querySelector(selector));
            }
        }

        function rafAsync() {
            return new Promise(resolve => {
                requestAnimationFrame(resolve);
            });
        }


    }

}

const maburn = new Maburn();
