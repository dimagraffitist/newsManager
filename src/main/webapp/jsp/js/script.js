function checkForm() {

    var el,
            elName,
            value,
            type;

    var errorList = [];

    var errorText = {
        1: resource.checkform_error_title,
        2: resource.checkform_error_date,
        3: resource.checkform_error_brief,
        4: resource.checkform_error_content,
        5: resource.checkform_error_date_format
    }

    for (var i = 0; i < document.newsForm.elements.length; i++) {
        el = document.newsForm.elements[i];
        elName = el.nodeName.toLowerCase();
        value = el.value;
        if (elName == "input") {

            type = el.type.toLowerCase();

            switch (type) {
                case "text" :
                    if (el.name == "newsMessage.title" && value == "")
                        errorList.push(1);
                    if (el.name == "newsMessage.date" && value == "")
                        errorList.push(2);
                    if (el.name == "newsMessage.date" && !validDate(value))
                        errorList.push(5);
                    break;
                default :
                    break;
            }
        } else if (elName == "textarea") {
            if (el.name == "newsMessage.brief" && value == "")
                errorList.push(3);
            if (el.name == "newsMessage.brief")
                briefCheckLength(el);
            if (el.name == "newsMessage.content" && value == "")
                errorList.push(4);
            if (el.name == "newsMessage.content")
                contentCheckLength(el);
        } else {

        }
    }

    if (!errorList.length)
        return true;

    var errorMsg = resource.checkform_error + "\n\n";
    for (i = 0; i < errorList.length; i++) {
        errorMsg += errorText[errorList[i]] + "\n";
    }
    alert(errorMsg);
    return false;
}

function validDate(date)
{
    if (resource.lang == 'en') {
        
        if (!/^\d\d\/\d\d\/\d{4}$/.test(date)) {
            return false;
        }

        var arrD = date.split("/");
        arrD[0] -= 1;
        var d = new Date(arrD[2], arrD[0], arrD[1]);
        if ((d.getFullYear() == arrD[2]) && (d.getMonth() == arrD[0]) && (d.getDate() == arrD[1])) {
            return true;
        } else {
            return false;
        }
    }
    if (resource.lang == 'ru') {

        if (!/^\d\d\/\d\d\/\d{4}$/.test(date)) {
            return false;
        }

        var arrD = date.split("/");
        arrD[1] -= 1;
        var d = new Date(arrD[2], arrD[1], arrD[0]);
        if ((d.getFullYear() == arrD[2]) && (d.getMonth() == arrD[1]) && (d.getDate() == arrD[0])) {
            var massDate = date.split("/");
            day = massDate[0];
            month = massDate[1];
            year = massDate[2];
            
            el = document.getElementsByName("newsMessage.date")[0];
            el.value = month+'/'+day+'/'+year;
            return true;
        } else {
            return false;
        }
        
    }
    return false;
}
function askDelete()
{
    return confirm(resource.askDelete);
}
function askAllDelete()
{
    var elLength = document.newsForm.elements.length;
    for (i = 0; i < elLength; i++)
    {
        var type = newsForm.elements[i].type;
        if (type == "checkbox" && newsForm.elements[i].checked) {
            return confirm(resource.askDeleteAll);
        }
    }
    alert(resource.askDeleteAll_error);
    return false;
}

function briefCheckLength(textArea) {
    var MaxLenght = 500;
    if (textArea.value.length > MaxLenght) {
        textArea.value = textArea.value.substring(0, MaxLenght);
    }
}
function contentCheckLength(textArea) {
    var MaxLenght = 2048;
    if (textArea.value.length > MaxLenght) {
        textArea.value = textArea.value.substring(0, MaxLenght);
    }
}
window.onload = function checkDeleteAllSubmit() {
    var currentPage = document.location.search.substring(1).split("=")[1];
    if (currentPage == "list") {
        if (document.getElementsByClassName('news_head')[0]) {
        }
        else {
            var obj = document.getElementsByName('method')[0];
            obj.style.display = "none";
        }
    }
}
