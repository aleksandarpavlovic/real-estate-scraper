let profileSlct = document.getElementById("profil");
let deleteBtn = document.getElementById("deleteBtn");
let editBtn = document.getElementById("editBtn");
let newBtn = document.getElementById("newBtn");
let displayBtn = document.getElementById("displayBtn");
let tipNekretnine = document.getElementById("tipNekretnine");
let profileName = document.getElementById("profileName");
let profileNameBox = document.getElementById("profileNameBox");
let typeRealty = document.getElementById("typeRealty");
let saveBtn = document.getElementById("saveBtn");
let cancelBtn = document.getElementById("cancelBtn");
let settings = document.getElementById("settings");
let sendBtn = document.getElementById("sendBtn");
let sortSlct = document.getElementById("sortSlct");
let paginationElement = document.getElementById("pagination");
let adsWrap = document.getElementById("oglasiWrap");

//link buttons
let firstPageBtn = document.getElementById("firstpagelink");
let prevPageBtn = document.getElementById("prevpagelink");
let currentPageBtn = document.getElementById("currentpagelink");
let nextPageBtn = document.getElementById("nextpagelink");
let lastPageBtn = document.getElementById("lastpagelink");

let settingsToggle = false;

let profileData = {profiles: [], selectedProfile: null, selectedProfileData: null};
let newProfileObj = {};
let newProfileArray;

let pageLinks = {first:null, prev:null, current:null, next:null, last:null};

window.onload = function () {
    editBtn.addEventListener("click", editProfile);
    deleteBtn.addEventListener("click", deleteProfile);
    newBtn.addEventListener("click", newProfile);
    displayBtn.addEventListener("click", displayProfile);  
    typeRealty.addEventListener("change", displayForm);
    saveBtn.addEventListener("click", saveProfile);
    cancelBtn.addEventListener("click", resetForm);
    settings.addEventListener("click", showSettings);
    sendBtn.addEventListener("click", sendSettings);
    profileSlct.addEventListener("change", changeProfileSelection);
    sortSlct.addEventListener("change", fetchRealtiesForProfile);
    firstPageBtn.addEventListener("click", fetchRealtiesFirstPage);
    prevPageBtn.addEventListener("click", fetchRealtiesPrevPage);
    nextPageBtn.addEventListener("click", fetchRealtiesNextPage);
    lastPageBtn.addEventListener("click", fetchRealtiesLastPage);
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
    fetchRealtiesForProfile();
}

function editProfile() {
    profileNameBox.style.display = "block";
    profileName.value = profileSlct.value;
    document.getElementById("tipNekretnine").style.display = "block";
    let profileDatas = profileData.selectedProfileData;
    let profileDataCriteria = profileDatas.search.criteria;

    if (profileDatas.topAdConditions) {
        for (let i = 0; i < profileDatas.topAdConditions.length; i++) {
            document.getElementById(profileDatas.topAdConditions[i].topAdName).checked = true;
            if (profileDatas.topAdConditions[i].topAdName == "PRICE_IN_TOP_N_PERCENT") {
                if (profileDatas.topAdConditions[i].parameter) {
                    document.getElementById("topPrice").value = profileDatas.topAdConditions[i].parameter;
                }
            }
            if (profileDatas.topAdConditions[i].topAdName == "PRICE_PER_M2_IN_TOP_N_PERCENT") {
                if (profileDatas.topAdConditions[i].parameter) {
                    document.getElementById("priceM2").value = profileDatas.topAdConditions[i].parameter;
                }
            }
        }
    }

    for (let i = 0; i < profileDataCriteria.length; i++) {
        if (profileDataCriteria[i].name.name == "Realty type") {
            if (profileDataCriteria[i].value.name == "APARTMENT") {
                document.getElementById("APARTMENT").selected = true;
                displayForm();
            }
            else if (profileDataCriteria[i].value.name == "HOUSE") {
                document.getElementById("HOUSE").selected = true;
                displayForm();
            }
            else if (profileDataCriteria[i].value.name == "LAND") {
                document.getElementById("LAND").selected = true;
                displayForm();
            }
        }
        if (profileDataCriteria[i].name.name == "Ad type") {
            document.getElementById(profileDataCriteria[i].value.name).checked = true;
        }
        if (profileDataCriteria[i].name.name == "Advertiser") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById(profileDataCriteria[i].values[j].name).checked = true;
            }
        }
        if (profileDataCriteria[i].name.name == "Floor") {
            if (profileDataCriteria[i].from.name) {
                document.getElementById("floorFrom").value = profileDataCriteria[i].from.name;
            }
            if (profileDataCriteria[i].to.name) {
                document.getElementById("floorTo").value = profileDataCriteria[i].to.name;
            }
        }
        if (profileDataCriteria[i].name.name == "Room count") {
            if (profileDataCriteria[i].from.name) {
                document.getElementById("roomFrom").value = profileDataCriteria[i].from.name;
            }
            if (profileDataCriteria[i].to.name) {
                document.getElementById("roomTo").value = profileDataCriteria[i].to.name;
            }
        }

        if (profileDataCriteria[i].name.name == "Price") {
            if (profileDataCriteria[i].from.name) {
                document.getElementById("cenaOd").value = profileDataCriteria[i].from.name;
            }
            if (profileDataCriteria[i].to.name) {
                document.getElementById("cenaDo").value = profileDataCriteria[i].to.name;
            }
        }

        if (profileDataCriteria[i].name.name == "Surface m2") {
            if (profileDataCriteria[i].from.name) {
                document.getElementById("kvadraturaOd").value = profileDataCriteria[i].from.name;
            }
            if (profileDataCriteria[i].to.name) {
                document.getElementById("kvadraturaDo").value = profileDataCriteria[i].to.name;
            }
        }
        if (profileDataCriteria[i].name.name == "Registration") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById("Registration").checked = true;
            }
        }
        if (profileDataCriteria[i].name.name == "Heating") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById(profileDataCriteria[i].values[j].name).checked = true;
            }
        }
        if (profileDataCriteria[i].name.name == "Apartment type") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById(profileDataCriteria[i].values[j].name).checked = true;
            }
        }
        if (profileDataCriteria[i].name.name == "Facilities") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById(profileDataCriteria[i].values[j].name).checked = true;
            }
        }
        if (profileDataCriteria[i].name.name == "Location") {
            for (let j = 0; j < profileDataCriteria[i].values.length; j++) {
                document.getElementById(profileDataCriteria[i].values[j].name).checked = true;
            }
        }

    }

    disableProfileBtns();
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
    let confirmation = confirm("Da li ste sigurni da želite da obrišete profil?");
    if (confirmation) {
        if (profileData.selectedProfile && profileData.selectedProfile.id) {
            let deleteProfileUrl = "http://localhost:8080/profiles/" + profileData.selectedProfile.id;

            let request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    deleteProfileAndReset(profileData.selectedProfile);
                    refreshProfileDisplay();
                    refreshProfilesList();
                    clearRealtiesDisplay();
                }
            }
            request.open("DELETE", deleteProfileUrl)
            request.setRequestHeader("Content-type", "application/json");
            request.send();
        }
    }
}

function refreshProfileDisplay() {
    // dole skloni onu formu sa kriterijumima
    document.getElementById("formWrap").style.display = "none";
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
    pageLinks = {};
    makePageLinks({});
}

function newProfile() {
    profileNameBox.style.display = "block";
    document.getElementById("tipNekretnine").style.display = "block";
    disableProfileBtns();
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
            getAds(myObj);
        }
    };
    request.open("POST", "ime gde saljem", true);
    request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    request.send(sendObj);
}

function hideProfileFormElements() {
    document.getElementById("formWrap").style.display = "none";
    document.getElementById("profileNameBox").style.display = "none";
    document.getElementById("tipNekretnine").style.display = "none";
}

function displayForm() {
    if (typeRealty.selectedIndex == 1) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "block";
        document.getElementById("kvadratura").style.display = "block";
        document.getElementById("brojSoba").style.display = "block";
        document.getElementById("gradnja").style.display = "block";
        document.getElementById("grejanje").style.display = "block";
        document.getElementById("apartmentType").style.display = "block";
        document.getElementById("dodatno").style.display = "block";
    }
    else if (typeRealty.selectedIndex == 2) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "none";
        document.getElementById("kvadratura").style.display = "block";
        document.getElementById("brojSoba").style.display = "block";
        document.getElementById("gradnja").style.display = "block";
        document.getElementById("grejanje").style.display = "block";
        document.getElementById("apartmentType").style.display = "none";
        document.getElementById("dodatno").style.display = "block";
    }
    else if (typeRealty.selectedIndex == 3) {
        document.getElementById("formWrap").style.display = "block";
        document.getElementById("spratnost").style.display = "none";
        document.getElementById("kvadratura").style.display = "none";
        document.getElementById("brojSoba").style.display = "none";
        document.getElementById("gradnja").style.display = "none";
        document.getElementById("grejanje").style.display = "none";
        document.getElementById("apartmentType").style.display = "none";
        document.getElementById("dodatno").style.display = "none";
    }
}

function saveProfile() {
    let option = document.createElement("option");
    option.text = profileName.value;
    option.value = profileName.value;
    profileSlct.add(option);

    if (profileName.value != "") {
        newProfileObj.name = profileName.value;
    }

    let search = {};
    search.realtyType = { name: "" };

    let typeRealty = document.getElementById("typeRealty");
    if (typeRealty.selectedIndex != 0) {
        newProfileObj.search = search;
        newProfileObj.search.realtyType.name = typeRealty.options[typeRealty.selectedIndex].value;
    }

    let criteria = [];
    newProfileObj.criteria = criteria;

    let roomObj = {
        "@type": "Range",
        name: {
            name: "Room count",
            display: "Broj soba"
        },
        criteriaType: "RANGE",
    }
    let roomFrom = document.getElementById("roomFrom");
    let roomTo = document.getElementById("roomTo");
    if (roomFrom.selectedIndex != "") {
        let from = { froom: { name: roomFrom.options[roomFrom.selectedIndex].value } };
        roomObj.from = from;
    }
    if (roomTo.selectedIndex != "") {
        let to = { to: { name: roomTo.options[roomTo.selectedIndex].value } };
        roomObj.to = to;
    }

    if (roomFrom.selectedIndex != "" || roomTo.selectedIndex != "") {
        criteria.push(roomObj);
    }

    let advertiserObj = {
        "@type": "Multi Value",
        name: {
            name: "Advertiser",
            display: "Oglasivac"
        },
        criteriaType: "MULTI_SELECT",
        values: []
    }

    let advertiser = document.getElementById("Advertiser");
    let name = {};
    for (let i = 0; i < advertiser.length; i++) {
        if (advertiser[i].checked) {
            name = { name: advertiser[i].value };
            advertiserObj.values.push(name);
        }
    }
    if (advertiserObj.value != "") {
        criteria.push(advertiserObj);
    }

    let price = {
        "@type": "Range",
        name: {
            name: "Price",
            display: "Cena"
        },
        criteriaType: "RANGE"
    };
    let priceFrom = { from: { name: "" } };
    let priceTo = { to: { name: "" } };
    if (document.getElementById("cenaOd").value != "") {
        priceFrom.from.name = document.getElementById("cenaOd").value;
        price.from = priceFrom;
    }
    if (document.getElementById("cenaDo").value != "") {
        priceTo.to.name = document.getElementById("cenaDo").value;
        price.to = priceTo;
    }
    if (document.getElementById("cenaOd").value != "" || document.getElementById("cenaDo").value != "") {
        criteria.push(price);
    }

    let floor = {
        "@type": "Range",
        name: {
            name: "Floor",
            display: "Spratnost"
        },
        criteriaType: "RANGE"
    };
    let floorFrom = document.getElementById("floorFrom");
    let floorTo = document.getElementById("floorTo");
    let floorFromObj = { from: { name: "" } };
    let floorToObj = { to: { name: "" } };
    if (floorFrom.selectedIndex != "") {
        floorFromObj.from.name = floorFrom.options[floorFrom.selectedIndex].value;
        floor.from = floorFromObj;
    }
    if (floorTo.selectedIndex != "") {
        floorToObj.to.name = floorTo.options[floorTo.selectedIndex].value;
        floor.to = floorToObj;
    }
    if (floorFrom.selectedIndex != "" || floorTo.selectedIndex != "") {
        criteria.push(floor);
    }

    let registration = {
        "@type": "Multi Value",
        name: {
            name: "Registration",
            display: "Uknjiženost"
        },
        criteriaType: "MULTI_SELECT",
        values: [
            {
                name: "REGISTERED"
            }
        ]
    };
    if (document.getElementById("Registration").checked) {
        criteria.push(registration);
    }

    let locationObj = {
        "@type": "Multi Value",
        name: {
            name: "Location",
            display: "Lokacija"
        },
        criteriaType: "MULTI_SELECT_TREE",
        values: []
    };

    let location = document.getElementById("Location");
    let locationName = {};
    for (let i = 0; i < location.length; i++) {
        if (location[i].checked) {
            locationName = { name: location[i].value };
            locationObj.values.push(locationName);
        }
    }

    if (locationObj.values != "") {
        criteria.push(locationObj);
    };

    let adTypeObj = {
        "@type": "Single Value",
        name: {
            name: "Ad type",
            display: "Tip oglasa"
        },
        criteriaType: "SINGLE_SELECT",
        value: {
            name: ""
        }
    };

    let adType = document.getElementById("adType");
    for (let i = 0; i < adType.length; i++) {
        if (adType[i].checked) {
            adTypeObj.value.name = (adType[i].value);
        }
    }

    if (adTypeObj.value.name != "") {
        criteria.push(adTypeObj);
    }

    let surface = {
        "@type": "Range",
        name: {
            name: "Surface m2",
            display: "Površina u m2"
        },
        criteriaType: "RANGE"
    };

    let surfaceFrom = {
        from: {
            name: "",
        }
    };
    let surfaceTo = {
        to: {
            name: "",
        }
    };
    if (document.getElementById("kvadraturaOd").value != "") {
        surfaceFrom.from.name = document.getElementById("kvadraturaOd").value;
        surface.from = surfaceFrom;
    }
    if (document.getElementById("kvadraturaDo").value != "") {
        surfaceTo.to.name = document.getElementById("kvadraturaDo").value;
        surface.to = surfaceTo;
    }

    if (document.getElementById("kvadraturaOd").value != "" || document.getElementById("kvadraturaDo").value != "") {
        criteria.push(surface);
    }

    let typeOfRealty = {
        "@type": "Single Value",
        name: {
            name: "Realty type",
            display: "Tip nekretnine"
        },
        criteriaType: "SINGLE_SELECT",
        value: {
            "name": ""
        }
    };
    if (typeRealty.selectedIndex != 0) {
        typeOfRealty.value.name = typeRealty.options[typeRealty.selectedIndex].value;
        criteria.push(typeOfRealty);
    }




    let build = document.getElementById("build");
    let buildName = {};
    let buildObj = {
        name: {
            name: "Build",
            display: "Gradnja"
        },
        criteriaType: "MULTI_SELECT",
        values: []
    };

    for (let i = 0; i < build.length; i++) {
        if (build[i].checked) {
            buildName = { name: build[i].value };
            buildObj.values.push(buildName);
        }
    }
    if (buildObj.values != "") {
        criteria.push(buildObj);
    }

    let heating = document.getElementById("heating");
    let heatingName = {};
    let heatingObj = {
        "name": {
            name: "Heating",
            display: "Grejanje"
        },
        criteriaType: "MULTI_SELECT",
        values: []
    };

    for (let i = 0; i < heating.length; i++) {
        if (heating[i].checked) {
            heatingName = { name: heating[i].value };
            heatingObj.values.push(heatingName);
        }
    }
    if (heatingObj.values != 0) {
        criteria.push(heatingObj);
    }

    let typeApartment = document.getElementById("typeApartment");
    let typeApartmentName = {};
    let typeApartmentObj = {
        name: {
            name: "Apartment type",
            display: "Tip stana"
        },
        criteriaType: "MULTI_SELECT",
        values: []
    };
    for (let i = 0; i < typeApartment.length; i++) {
        if (typeApartment[i].checked) {
            typeApartmentName = { name: typeApartment[i].value };
            typeApartmentObj.values.push(typeApartmentName);
        }
    }

    let facilities = document.getElementById("facilities");
    let facilitiesName = {};
    let facilitiesObj = {
        name: {
            name: "Facilities",
            display: "Dodatno"
        },
        criteriaType: "MULTI_SELECT",
        values: []
    };
    for (let i = 0; i < facilities.length; i++) {
        if (facilities[i].checked) {
            facilitiesName = { name: facilities[i].value };
            facilitiesObj.values.push(facilitiesName);
        }
    }
    if (facilitiesObj.values != "") {
        criteria.push(facilitiesObj);
    }

    let topAdsObj = { topAdConditions: [] };
    let topAds = document.getElementById("topAds");
    if (topAds[0].checked) {
        topAdsObj.topAdConditions.push({
            "@type": "TopAdCondition",
            topAdName: "NEW_AD"
        });
    }
    if (topAds[1].checked) {
        topAdsObj.topAdConditions.push({
            "@type": "TopAdCondition",
            topAdName: "PRICE_DROP"
        });
    }
    if (topAds[2].checked) {
        topAdsObj.topAdConditions.push({
            "@type": "Parameterized",
            topAdName: "PRICE_IN_TOP_N_PERCENT",
            parameter: document.getElementById("priceM2").value
        });
    }
    if (topAds[3].checked) {
        topAdsObj.topAdConditions.push({
            "@type": "Parameterized",
            topAdName: "PRICE_PER_M2_IN_TOP_N_PERCENT",
            parameter: document.getElementById("topPrice").value
        });
    }

    if (topAdsObj.topAdConditions != "") {
        newProfileObj.topAdConditions = topAdsObj;
    }

    hideProfileFormElements();
    enableProfileBtns();
    console.log(newProfileObj);
}

function resetForm() {
    let confirmation = confirm("Da li ste sigurni da želite da odustanete?");
    if (confirmation) {
        let forms = document.querySelectorAll('aside>form');
        for (let i = 0; i < forms.length; i++) {
            forms[i].reset();
        }
        hideProfileFormElements();
        enableProfileBtns();
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

function fetchRealtiesForProfile() {
    return fetchRealties(formRealtiesUrl());
}

function fetchRealtiesFirstPage() {
    if (pageLinks.first)
        return fetchRealties(pageLinks.first);
}

function fetchRealtiesPrevPage() {
    if (pageLinks.prev)
        return fetchRealties(pageLinks.prev);
}

function fetchRealtiesNextPage() {
    if (pageLinks.next)
        return fetchRealties(pageLinks.next);
}

function fetchRealtiesLastPage() {
    if (pageLinks.last)
        return fetchRealties(pageLinks.last);
}

function fetchRealties(url) {
    let xmlhttp = new XMLHttpRequest();
    
    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let niz = JSON.parse(this.responseText);
            makePageLinks(JSON.parse(this.getResponseHeader("Link")));
            getAds(niz);
        }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function makePageLinks(linkHeader) {
    if (linkHeader.first) {
        pageLinks.first = linkHeader.first;
    } else {
        pageLinks.first = null;
    }

    if (linkHeader.prev) {
        pageLinks.prev = linkHeader.prev;
    } else {
        pageLinks.prev = null;
    }

    if (linkHeader.current) {
        currentPageLink = document.getElementById("currentpagelink");
        currentPageLink.text = linkHeader.current;
    } else {
        currentPageLink = document.getElementById("currentpagelink");
        currentPageLink.text = "1";
    }

    if (linkHeader.next) {
        pageLinks.next = linkHeader.next;
    } else {
        pageLinks.next = null;
    }

    if (linkHeader.last) {
        pageLinks.last = linkHeader.last;
    } else {
        pageLinks.last = null;
    }
}

function formRealtiesUrl() {
    let url = "http://localhost:8080/realties?searchid=";
    if (profileData && profileData.selectedProfile && profileData.selectedProfile.id) {
        url += profileData.selectedProfile.id;
    } else {
        url += "0";
    }
    url += "&page=1&sort=" + sortSlct.options[sortSlct.selectedIndex].value;

    return url;
}
function clearRealtiesDisplay() {
    if (adsWrap.childNodes && adsWrap.childNodes[0])
        adsWrap.removeChild(adsWrap.childNodes[0]);
}

function getAds(niz) {
    clearRealtiesDisplay();

    let adsDiv = document.createElement("div");
    adsWrap.appendChild(adsDiv);
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
    adsDiv.appendChild(div);
    div.innerHTML = ispis;
    
    }
    
}

function getFormApartment() {
    let xmlhttp = new XMLHttpRequest();

    xmlhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let obj = JSON.parse(this.responseText);
            let html = parseCities(obj.locationDefinition.locations);
            document.getElementById("Location").innerHTML += html;
        }
    };
    // TODO zameni restom
    xmlhttp.open("GET", "json/new-15.json", true);
    xmlhttp.send();
}

getFormApartment();

function parseCities(cities) {
    let html = "";
    html += "<ul>";
    for (let i = 0; i < cities.length; i++) {
        let city = cities[i];
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
    return "<input id='" + city.name + "' type='checkbox'  value='" + city.name + "' /><label for='" + city.display + "'>" + city.display + "</label>";
}

function enableProfileBtns() {
    profileSlct.disabled = false;
    deleteBtn.disabled = false;
    editBtn.disabled = false;
    displayBtn.disabled = false;
    newBtn.disabled = false;
}

function disableProfileBtns() {
    profileSlct.disabled = true;
    deleteBtn.disabled = true;
    editBtn.disabled = true;
    displayBtn.disabled = true;
    newBtn.disabled = true;
}
