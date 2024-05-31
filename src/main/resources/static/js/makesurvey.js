document.addEventListener('DOMContentLoaded', function() {
    const addQuestionBtn = document.getElementById('addQuestionBtn');
    const questionsContainer = document.getElementById('questionsContainer');
    const submitBtn = document.getElementById('submitBtn');
  
    addQuestionBtn.addEventListener('click', addQuestion);
    submitBtn.addEventListener('click', submitForm);
  
    function addQuestion() {
      const questionDiv = document.createElement('div');
      questionDiv.classList.add('question');
  
      const input = document.createElement('input');
      input.setAttribute('type', 'text');
      input.setAttribute('placeholder', '请输入问题');
      input.classList.add('question-input');
  
      const typeSelect = document.createElement('select');
      typeSelect.classList.add('question-type');
      const option1 = document.createElement('option');
      option1.textContent = '单选题';
      option1.setAttribute('value', 'single');
      const option2 = document.createElement('option');
      option2.textContent = '多选题';
      option2.setAttribute('value', 'multiple');
      const option3 = document.createElement('option');
      option3.textContent = '填空题';
      option3.setAttribute('value', 'open');
      typeSelect.appendChild(option1);
      typeSelect.appendChild(option2);
      typeSelect.appendChild(option3);
      typeSelect.addEventListener('change', function() {
        toggleOptionsInput(this.value, optionsContainer, addOptionBtn);
      });
  
      const optionsContainer = document.createElement('div');
      optionsContainer.classList.add('options-container');
  
      const addOptionBtn = document.createElement('button');
      addOptionBtn.textContent = '添加选项';
      addOptionBtn.classList.add('add-option-btn');
      addOptionBtn.style.display = 'none'; // Initially hide the add option button
      addOptionBtn.addEventListener('click', function() {
        addOption(optionsContainer);
      });
  
      const deleteQuestionBtn = document.createElement('button');
      deleteQuestionBtn.textContent = '删除该问题';
      deleteQuestionBtn.classList.add('delete-question-btn');
      deleteQuestionBtn.addEventListener('click', function() {
        questionsContainer.removeChild(questionDiv);
      });
  
      questionDiv.appendChild(input);
      questionDiv.appendChild(typeSelect);
      questionDiv.appendChild(optionsContainer);
      questionDiv.appendChild(addOptionBtn);
      questionDiv.appendChild(deleteQuestionBtn);
      questionsContainer.appendChild(questionDiv);
      
      toggleOptionsInput(typeSelect.value, optionsContainer, addOptionBtn);
    }
  
    function toggleOptionsInput(type, container, button) {
      if (type === 'open') {
        container.innerHTML = ''; // Clear options input for open-ended questions
        button.style.display = 'none'; // Hide the add option button
      } else {
        container.innerHTML = ''; // Clear options input for single/multiple choice questions
        button.style.display = 'block'; // Show the add option button
        addOption(container); // Add an initial option input
      }
    }
  
    function addOption(container) {
      const optionDiv = document.createElement('div');
      optionDiv.classList.add('option');
  
      const input = document.createElement('input');
      input.setAttribute('type', 'text');
      input.setAttribute('placeholder', '输入选项内容');
      input.classList.add('option-input');
  
      const deleteOptionBtn = document.createElement('button');
      deleteOptionBtn.textContent = '删除';
      deleteOptionBtn.classList.add('delete-option-btn');
      deleteOptionBtn.addEventListener('click', function() {
        container.removeChild(optionDiv);
      });
  
      optionDiv.appendChild(input);
      optionDiv.appendChild(deleteOptionBtn);
      container.appendChild(optionDiv);
    }
  
    function submitForm() {
      const questions = document.querySelectorAll('.question');
      const formData = [];
  
      questions.forEach(function(question) {
        const questionData = {
          title: question.querySelector('.question-input').value,
          type: question.querySelector('.question-type').value,
          options: []
        };
  
        if (questionData.type !== 'open') {
          const options = question.querySelectorAll('.option-input');
          options.forEach(function(option) {
            questionData.options.push(option.value);
          });
        }
  
        formData.push(questionData);
      });
  
      console.log(formData);
      // Here you can handle the form submission, e.g., send data to a server
    }
  });
  const titleInput = document.createElement('input');
titleInput.setAttribute('type', 'text');
titleInput.setAttribute('placeholder', 'Enter your questionnaire title');
titleInput.classList.add('title-input');
titleInput.addEventListener('change', function() {
  questionnaireTitle = this.value;
});
const questionData = {
    title: questionnaireTitle,
    question: question.querySelector('.question-input').value,
    type: question.querySelector('.question-type').value,
    options: []
  };
  

  