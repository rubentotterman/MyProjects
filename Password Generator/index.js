let passwordChars = [
  ">",
  "n",
  "J",
  "j",
  "2",
  "e",
  "G",
  "R",
  "r",
  "<",
  "y",
  "!",
  "d",
  "D",
  "v",
  "&",
  "i",
  "a",
  "l",
  "x",
  "W",
  "@",
  "v",
  "e",
  "P",
  "U",
  "w",
  "K",
  "Q",
  "Y",
  "?",
  "V",
  "L",
  ")",
  "N",
  "c",
  "R",
  "^",
  "p",
  "I",
  "D",
  "f",
  "U",
  "Z",
  "?",
  "+",
  "M",
];

let firstInput = document.getElementById("first-left-input");
let secondInput = document.getElementById("first-right-input");
let thirdInput = document.getElementById("second-left-input");
let fourthInput = document.getElementById("second-right-input");
let length = 0;
const lengthInput = document.getElementById("length-input");

const generate = () => {
  generateRandomPasswords();
};

// Main function that render and show the passwords in the screen.

function getRandomPasswords() {
  let length = checkMinLegth();
  for (let i = 0; i < 4; i++) {
    if (firstInput.value.length < length) {
      for (let i = 0; i < length; i++) {
        firstInput.value += passwordChars[getRandomIndex()];
      }
    } else if (secondInput.value.length < length) {
      for (let i = 0; i < length; i++) {
        secondInput.value += passwordChars[getRandomIndex()];
      }
    } else if (thirdInput.value.length < length) {
      for (let i = 0; i < length; i++) {
        thirdInput.value += passwordChars[getRandomIndex()];
      }
    } else if (fourthInput.value.length < length) {
      for (let i = 0; i < length; i++) {
        fourthInput.value += passwordChars[getRandomIndex()];
      }
    }
  }
}

// Function that randomized the characters selection in the array
function getRandomIndex() {
  let randomIndex = Math.floor(Math.random() * 16) + 1;
  return randomIndex;
}

// Function that allows the program generate a new passwords everytime is hit.
function generateRandomPasswords() {
  firstInput.value = "";
  secondInput.value = "";
  thirdInput.value = "";
  fourthInput.value = "";
  getRandomPasswords();
}

// Function for checking the min length and the maximum
function checkMinLegth() {
  length = parseInt(lengthInput.value);

  if (length < 6 || length > 21) {
    alert("The min length for a password is 6 and maximum 21");
    lengthInput.value = 15;
  } else {
    return length;
  }
}

function copyPassword(inputClicked) {
  inputClicked.select();
  document.execCommand("copy");
  alert("Password copied");
}
