/**
 * Function adds new "answer" input field and checkbox to the current question.
 * 
 * @param e -
 *            event
 * 
 */
function addAnswer(e) {
	
	const questionsContainer = e.target
		.parentElement
	    .parentElement
	    .parentElement
	    .querySelector('.questions');
	
	 const answersContainer = e.target
	   .parentElement
	   .querySelector('.answers');
	  
	 const questionContainer = e.target
	   .parentElement
	   .querySelector('.questionInput');
	 
	  const questionId = questionContainer.name.split('_').pop();

	  const answerId =  answersContainer
	    .querySelectorAll('.answer').length + 1;
	  
	  const newAnswerName = `answer_${questionId}_${answerId}`;
	  const newCheckboxName = `box_${questionId}_${answerId}`;
	  
	  const newAnswerElement = document.createElement('input');
	  const newCheckboxElement = document.createElement('input');
	  const text = document.createElement('strong');
	  const br = document.createElement('br');
	  
	  text.innerHTML+=questionsContainer.getAttribute('id').split('_')[1]+answerId+": ";

	  newAnswerElement.size = 45;
	  newAnswerElement.name = newAnswerName;
	  newAnswerElement.required = 'required';
	  newAnswerElement.type = 'text';
	  newAnswerElement.classList.add('answer');
	  
	  newCheckboxElement.type= 'checkbox';
	  newCheckboxElement.name = newCheckboxName;
	  newCheckboxElement.value = 'correct';
	  
	  answersContainer.appendChild(br);
	  answersContainer.appendChild(text);
	  answersContainer.appendChild(newAnswerElement);
	  answersContainer.appendChild(newCheckboxElement);

}
/**
 * 
 * Function adds new "question" input field, "answer" input field to this
 * question and checkbox to the answer.
 * 
 * @param e -
 *            event
 * 
 */
function addQuestion(e) {
	
	const questionsContainer = e.target
	   .parentElement
	   .querySelector('.questions');
	
	const questionId =  questionsContainer
    .querySelectorAll('.question').length + 1;
	
	const newQuestionName = `question_${questionId}`;
	const newAnswerName = `answer_${questionId}_1`;
	const newCheckboxName = `box_${questionId}_1`;
	const newAnswersButtonName = `answerButton_question${questionId}`;

	const newQuestionElement = document.createElement('input');
	const newAnswerElement = document.createElement('input');
	const newCheckboxElement = document.createElement('input');
	const newAnswersButton = document.createElement('input');
	const newAnswersContainer = document.createElement('div');
	const newQuestionContainer = document.createElement('div');
	
	const questionText = document.createElement('strong');
	const answerText = document.createElement('strong');
	const br = document.createElement('br');
	
	const answersLoc = questionsContainer.getAttribute('id').split('_')[1];
	
	questionText.innerHTML+=questionsContainer.getAttribute('id').split('_')[0]+questionId+": ";
	answerText.innerHTML+=answersLoc+"1: ";

	newQuestionElement.size = 45;
	newQuestionElement.name = newQuestionName;
	newQuestionElement.required = 'required';
	newQuestionElement.type = 'text';
	newQuestionElement.classList.add('questionInput');
	
	newAnswerElement.size = 45;
	newAnswerElement.name = newAnswerName;
	newAnswerElement.required = 'required';
	newAnswerElement.type = 'text';
	newAnswerElement.classList.add('answer');

	newCheckboxElement.type= 'checkbox';
	newCheckboxElement.name = newCheckboxName;
	newCheckboxElement.value = 'correct';
		
	newAnswersButton.classList.add('answers');
	newAnswersButton.type = 'button';
	newAnswersButton.name = newAnswersButtonName;
	newAnswersButton.value = questionsContainer.getAttribute('id').split('_')[2];
	newAnswersButton.setAttribute('onclick','addAnswer(event);');
		
	newAnswersContainer.classList.add('answers');
		
	newAnswersContainer.appendChild(answerText);
	newAnswersContainer.appendChild(newAnswerElement);
	newAnswersContainer.appendChild(newCheckboxElement);
	
	newQuestionContainer.classList.add('question');
		
	newQuestionContainer.appendChild(questionText);
	newQuestionContainer.appendChild(newQuestionElement);
	newQuestionContainer.appendChild(newAnswersContainer);
	newQuestionContainer.appendChild(newAnswersButton);
	
	questionsContainer.appendChild(newQuestionContainer);
	questionsContainer.appendChild("asd");

}
/**
 * Sticky footer
 */
document.addEventListener("DOMContentLoaded", function (event) {
    var element = document.getElementById('content_container');
    var height = element.offsetHeight;
    if (height < screen.height) {
        document.getElementById("footer").classList.add('stikybottom');
    }
}, false);

function checkPass()
{
    // Store the password field objects into variables ...
    var pass1 = document.getElementById('pass1');
    var pass2 = document.getElementById('pass2');
    // Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    // Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    
    var ok = true;
    // Compare the values in the password field
    // and the confirmation field
    if(pass1.value == pass2.value){
        // The passwords match.
        // Set the color to the good color and inform
        // the user that they have entered the correct password
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match!";
    }else{
        // The passwords do not match.
        // Set the color to the bad color and
        // notify the user.
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!";
        ok = false;
    }
    return ok;
}  