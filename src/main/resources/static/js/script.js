let profileSlct = document.getElementById("profil");
let deleteBtn = document.getElementById("deleteBtn");
let editBtn = document.getElementById("editBtn");
let newBtn = document.getElementById("newBtn");
let displayBtn = document.getElementById("displayBtn");
let tipNekretnine = document.getElementById("tipNekretnine");
let nazivProfila = document.getElementById("nazivProfila");
let imeProfila = document.getElementById("imeProfila");
let vrstaNekretnine = document.getElementById("vrstaNekretnine");
let saveBtn = document.getElementById("saveBtn");
let cancelBtn = document.getElementById("cancelBtn");
let settings = document.getElementById("settings");
let sendBtn = document.getElementById("sendBtn");

let settingsToggle = false;

let profileData = {profiles: [], selectedProfile: null, selectedProfileData: null};

window.onload = function () {
    editBtn.addEventListener("click", editProfile);
    deleteBtn.addEventListener("click", deleteProfile);
    newBtn.addEventListener("click", newProfile);
    displayBtn.addEventListener("click", displayProfile);  
    vrstaNekretnine.addEventListener("change", displayForm);
    saveBtn.addEventListener("click", saveProfile);
    cancelBtn.addEventListener("click", resetForm);
    settings.addEventListener("click", showSettings);
    sendBtn.addEventListener("click", sendSettings);
    profileSlct.addEventListener("change", changeProfileSelection)
    fetchProfilesInfo();
}

function changeProfileSelection() {
    let selectedProfileId = profileSlct.options[profileSlct.selectedIndex].value;
    for (var i=0; i<profileData.profiles.length; i++) {
        if (profileData.profiles[i].id == selectedProfileId) {
            profileData.selectedProfile = profileData.profiles[i];
            break;
        }
    }
    fetchProfile();
}

function editProfile() {
    imeProfila.style.display = "block";
    nazivProfila.value = profileSlct.value;
    profileSlct.disabled = true;
    deleteBtn.disabled = true;
    editBtn.disabled = true;
    displayBtn.disabled = true;
    newBtn.disabled = true;
    
    //pozivanje prethodno cekirane forme, da korisnik moze da je menja
}

function fetchProfilesInfo() {
    let getProfilesUrl = "http://localhost:8080/profiles?fields=id,name";
    let request = new XMLHttpRequest();
    request.onload = function() {
        if (this.status == 200) {
            profileData.profiles = JSON.parse(this.responseText);
            refreshProfilesList();
        }
    }
    request.open("GET", getProfilesUrl)
    request.setRequestHeader("Content-type", "application/json");
    request.send();
}

function refreshProfilesList() {
    profileSlct.innerHTML = "";

    // add default
    var defaultOption = document.createElement("option");
    defaultOption.text = "Izaberite profil"
    defaultOption.selected = true;
    defaultOption.disabled = true;
    defaultOption.style.visibility = "hidden";
    profileSlct.add(defaultOption);

    // populate
    profileData.profiles.forEach(function(profile) {
        var option = document.createElement("option");
        option.value = profile.id;
        option.text = profile.name;
        profileSlct.add(option);
    });
}

function fetchProfile() {
    if (profileData.selectedProfile && profileData.selectedProfile.id) {
        let getProfileUrl = "http://localhost:8080/profiles/" + profileData.selectedProfile.id;
        let request = new XMLHttpRequest();
        request.onload = function() {
            if (this.status == 200) {
                profileData.selectedProfileData = JSON.parse(this.responseText);
                // prikaz profile data
            }
        }
        request.open("GET", getProfileUrl, true)
        request.setRequestHeader("Content-type", "application/json");
        request.send();
    }
}

function deleteProfile() {
    if (profileData.selectedProfile && profileData.selectedProfile.id) {
        let deleteProfileUrl = "http://localhost:8080/profiles/" + profileData.selectedProfile.id;

        let request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                deleteProfileAndReset(profileData.selectedProfile);
                refreshProfileDisplay();
                refreshProfilesList();
            }
        }
        request.open("DELETE", deleteProfileUrl)
        request.setRequestHeader("Content-type", "application/json");
        request.send();
    }
}

function refreshProfileDisplay() {
    // dole skloni onu formu sa kriterijumima
}

function deleteProfileAndReset(selectedProfile) {
    if (!profileData.profiles)
        return;

    let profileIndex = 0;
    for (var i=0; i<profileData.profiles.length; i++) {
        if (profileData.profiles[i] == selectedProfile) {
            profileIndex = i;
            break;
        }
    }
    profileData.profiles.splice(profileIndex, 1);

    profileData.selectedProfile = null;
    profileData.selectedProfileData = null;
}

function newProfile() {
    imeProfila.style.display = "block";
    profileSlct.disabled = true;
    deleteBtn.disabled = true;
    editBtn.disabled = true;
    displayBtn.disabled = true;
    newBtn.disabled = true;
    //prikazati praznu formu
}

function displayProfile() {
    let index = profileSlct.selectedIndex;
    let profileName = profileSlct.options[index].text;
    let obj = { "profileName":profileName};
    let sendObj = JSON.stringify(obj);
    let request = new XMLHttpRequest();
    request.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            let myObj = JSON.parse(this.responseText);
            ispisiPodatke(myObj);
        }
    };
    request.open("POST", "ime gde saljem", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(sendObj);
}

function displayForm() {
    if(vrstaNekretnine.selectedIndex == 1) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "block";
        document.getElementById("kvadratura").style.display = "block";
        document.getElementById("brojSoba").style.display = "block";
        document.getElementById("gradnja").style.display = "block";
        document.getElementById("grejanje").style.display = "block";
    }
    else if (vrstaNekretnine.selectedIndex == 2) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "none";
    }
    else if (vrstaNekretnine.selectedIndex == 3) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "none";
        document.getElementById("kvadratura").style.display = "none";
        document.getElementById("brojSoba").style.display = "none";
        document.getElementById("gradnja").style.display = "none";
        document.getElementById("grejanje").style.display = "none";
    }
}

function saveProfile() {
    let option = document.createElement("option");
    option.text = nazivProfila.value;
    option.value = nazivProfila.value;
    profileSlct.add(option);
    document.getElementById("nazivProfila").value = "";
    profileSlct.disabled = false;
    deleteBtn.disabled = false;
    editBtn.disabled = false;
    displayBtn.disabled = false;
    newBtn.disabled = false;

    let newProfile = "";
         
    //dodaj u lokalStoridz i posalji u bazu
}

function resetForm() {
    let forms = document.querySelectorAll('form');
    for (let i = 0; i < forms.length; i++) {
        forms[i].reset();
    }
}

function showSettings() {
    let settingsVisible = toggleSettingsAndDisplay();
    if (settingsVisible) {
        request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let settingsObj = JSON.parse(this.responseText);
                displaySettings(settingsObj);
            }
        }
        request.open("GET", "http://localhost:8080/settings");
        request.setRequestHeader("Content-type", "application/json");
        request.send();
    }

}

function toggleSettingsAndDisplay() {
    settingsToggle = !settingsToggle;
    let settingsBox = document.getElementById("settingsBox");
    if (settingsToggle) {
        settingsBox.style.display = "block";
    } else {
        settingsBox.style.display = "none";
    }
    return settingsToggle;
}

function displaySettings(settingsObj) {
    if (settingsObj.scheduledPeriod) {
        document.getElementById("schedule").value = settingsObj.scheduledPeriod;
    }
    if (settingsObj.emailList) {
        document.getElementById("email").value = settingsObj.emailList.join(", ");
    }
}

function sendSettings () {
    let chosenSettings = {
        scheduledPeriod: document.getElementById("schedule").value,
        emailList: document.getElementById("email").value.split(",").map(email => email.trim())
    };
    let chosenSettingsObj = JSON.stringify(chosenSettings);
    request = new XMLHttpRequest();
    request.open("POST", "http://localhost:8080/settings");
    request.setRequestHeader("Content-type", "application/json");
    request.send(chosenSettingsObj);

    toggleSettingsAndDisplay();
}


function stanovi() {
    let xmlhttp = new XMLHttpRequest();
    
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let niz = JSON.parse(this.responseText);
            ispisiPodatke(niz);
        }
    };
    xmlhttp.open("GET", "json/realties.json", true);
    xmlhttp.send();
}

function ispisiPodatke(niz) {
    
    let ispis = "";
    for (let i = 0; i < niz.length; i++) {
        ispis = `<article class="slikaDatumOglasivac">
        <a href=${niz[i].url.display} target="_blank"><img src=${niz[i].imageUrl.display} class = "slikaOglasa"></a>
        <p>${niz[i].publishDate.display} |<span> ${niz[i].advertiser.display}</span></p>
    </article>
    <article class="stanInfo">
        <p><a href=${niz[i].url.display} target="_blank"><span class="naslovOglasa">${niz[i].title.display}</span><span class="cena">${niz[i].price.display} €</span></a></p>
        <p class="lokacija">${niz[i].location.display}</p>
        <p class="tipKvadraturaSoba"><span class="tipNekretnine"> ${niz[i].realtyType} </span>|<span class="kvadratura"> ${niz[i].surfaceArea.display} </span>|<span class="brSoba"> ${niz[i].rooms.display}</span></p>
        <p class="opis">${niz[i].description.display}</p>
    </article>`;
    let div = document.createElement("div");
    div.className = "placeholder";
    document.getElementById("oglasiWrap").appendChild(div);
    div.innerHTML = ispis;
    
    }
    
}
stanovi();

function getFormApartment() {
    let xmlhttp = new XMLHttpRequest();
    
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let obj = JSON.parse(this.responseText);
            let html = parseCities(obj.locationDefinition.locations);
            document.getElementById("locationCheckbox").innerHTML += html;
        }
    };
    xmlhttp.open("GET", "json/new-15.json", true);
    xmlhttp.send();
}

getFormApartment();

function parseCities(cities) {
    let html = "";
    html += "<ul>";
    for (let i = 0; i < cities.length; i++) {
        let city = cities[i];
        console.log(city);
        let input = createCheck(city);

        html += "<li>" + input;
        if (city.sublocations && city.sublocations.length > 0) {
            html += parseCities(city.sublocations);
        }
        html += "</li>";
    }
    html += "</ul>"
    return html;
}

function createCheck(city) {
    return "<input type='checkbox' id='" + city.name + "' value='" + city.display + "' /><label for='" + city.display + "'>" + city.display + "</label>";
}