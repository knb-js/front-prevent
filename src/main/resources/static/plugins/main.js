// CARDHOLDER NAME
let nameCard = document.querySelector('.card_details-name');
let nameInput = document.querySelector('#cardHolder');
let nameErrorDiv = document.querySelector('.form_cardHolder--error');

// CARD NUMBER
let numberCard = document.querySelector('.card_number');
let numberInput = document.querySelector('#cardNumber');
let numberErrorDiv = document.querySelector('.form_inputNumber--error');

// MM
let monthCard = document.querySelector('.card_month');
let monthInput = document.querySelector('#cardMonth');
let monthErrorDiv = document.querySelector('.form_input-mm--error');

// YY
let yearCard = document.querySelector('.card_year');
let yearInput = document.querySelector('#cardYear');
let yearErrorDiv = document.querySelector('.form_input-yy--error');

// CVC
let cvcCard = document.querySelector('.card-back_cvc');
let cvcInput = document.querySelector('#cardCvc');
let cvcErrorDiv = document.querySelector('.form_input-cvc-error');

// Ingreso dinamico del nombre
nameInput.addEventListener('input', ()=>{
    if(nameInput.value == ''){
        nameCard.innerText='JANE APPLESEED'
    }else{
        nameCard.innerText = nameInput.value;
    }
});

// Ingreso dinamico del numero
numberInput.addEventListener('input', event=>{

    let inputValue = event.target.value;

    // Actualizando graficamente la tarjeta
    numberCard.innerText = numberInput.value;

    //Validando una letra
    let regExp = /[A-z]/g;
    if(regExp.test(numberInput.value)){
        showError(numberInput, numberErrorDiv, 'Solo se pueden ingresar numeros');
    }else{
        //agregando espacios cada 4 digitos
        numberInput.value = inputValue.replace(/\s/g,'').replace(/([0-9]{4})/g,'$1 ').trim();
        showError(numberInput, numberErrorDiv, '', false);
    }

    //Mostrando los cero por defecto
    if(numberInput.value == ''){
        numberCard.innerText='0000 0000 0000 0000';
    }
});

// Ingreso dinamico del mes
monthInput.addEventListener('input', ()=>{
    monthCard.innerText = monthInput.value;
    validateLetters(monthInput, monthErrorDiv);
    if(monthInput.value == ''){
        monthCard.innerText='00';
    }
})


// Ingreso dinamico del año
yearInput.addEventListener('input', ()=>{
    yearCard.innerText = yearInput.value;
    validateLetters(yearInput, yearErrorDiv);
    if(yearInput.value == ''){
        yearCard.innerText='00';
    }
})

// Ingreso dinamico del cvc
cvcInput.addEventListener('input', ()=>{
    cvcCard.innerText = cvcInput.value;
    validateLetters(cvcInput, cvcErrorDiv);
    if(cvcInput.value == ''){
        cvcCard.innerText='000';
    }
})


// Boton
let confirmBtn = document.querySelector('.form_submit');

let nameValidation = false;
let numberValidation = false;
let monthValidation = false;
let yearValidation = false;
let cvcValidation = false;

// Secciones de Formulario y Thanks
let formSection = document.querySelector('.form');
let thanksSection = document.querySelector('.thanks-section');

confirmBtn.addEventListener('click', event=>{
    event.preventDefault();

    // Validar Name
    if(verifyIsFilled(nameInput, nameErrorDiv)){
        nameValidation = true;
    }else{
        nameValidation = false;
    }

    // Validar Numero
    if(verifyIsFilled(numberInput, numberErrorDiv) == true){
        if(numberInput.value.length == 19){
            showError(numberInput, numberErrorDiv, '', false);
            numberValidation = true;
        }else{
            showError(numberInput, numberErrorDiv, 'Numero incorrecto');
            numberValidation = false;
        }
    };

    // Validar Mes
    if(verifyIsFilled(monthInput, monthErrorDiv)){
        if(parseInt(monthInput.value)>0 && parseInt (monthInput.value)<=12){
            showError(monthInput, monthErrorDiv, '', false);
            monthValidation = true;
        }else{
            showError(monthInput, monthErrorDiv, 'Mes Incorrecto');
            monthValidation = false;
        }
    }
        
    // Validar año
    if(verifyIsFilled(yearInput, yearErrorDiv)){
        if(parseInt(yearInput.value)>=22 && parseInt(yearInput.value)<=27){
            showError(yearInput, yearErrorDiv, '', false);
            yearValidation = true;
        }else{
            showError(yearInput, yearErrorDiv, 'Año incorrecto');
            yearValidation = false;
        }
    }

    // Validar cvc
    if(verifyIsFilled(cvcInput, cvcErrorDiv)){
        if(cvcInput.value.length==3){
            showError(cvcInput, cvcErrorDiv, '', false);
            cvcValidation = true;
        }else{
            showError(cvcInput, cvcErrorDiv, 'CVC incorrecto');
            cvcValidation = false;
        }
    }

    if(nameValidation == true && numberValidation == true && monthValidation == true && yearValidation == true && cvcValidation == true){
//        formSection.style.display = 'none';
		let listCart = JSON.parse(localStorage.getItem('listCart')) || [];
		let total = listCart.reduce((n, {precio} ) => n + precio,0)
//        thanksSection.style.display = 'block';

console.log(total)
		handlePayment(total);
		

    } 

});


	const handlePayment = (listCart) => {
	    $.ajax({
	    	url: `/ajax/payment`,
	        type: "POST",
	        data: JSON.stringify(listCart),
	        dataType: "json",
	        async: false,
	        contentType: "application/json; charset=utf-8",
	        success: (data) => {
	           console.log(data);
	        }
		});
	}

// Funciones

function showError(divInput, divError, msgError, show = true){
    if(show){
        divError.innerText = msgError;
        divInput.style.borderColor = '#FF0000';
    }else{
        divError.innerText = msgError;
        divInput.style.borderColor='hsl(270, 3%, 87%)';
    }
}

function verifyIsFilled(divInput, divError){
    if(divInput.value.length > 0){
        showError(divInput, divError, "", false);
        return true;
    }else{
        showError(divInput, divError, "No puede estar en blanco");
        return false;
    }
}

function validateLetters(input, divError){
    let regExp = /[A-z]/g;
    if(regExp.test(input.value)){
        showError(input, divError, 'Solo se pueden ingresar numeros');
    }else{
        showError(input, divError, '', false);
    }
}





