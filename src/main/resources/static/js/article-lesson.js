var nodeList = document.getElementsByClassName("code");

if (nodeList !== null) {
  for (var i = 0; i < nodeList.length; i++) {
    var node = nodeList[i];
    node.className = "";

    // Create a new div element
    var newDiv = document.createElement("div");

    // Create a new code element
    var codeElement = document.createElement("code");

    // Append the contents of node to the newDiv
    newDiv.innerHTML = node.innerHTML;

    // Append the newDiv to the codeElement
    codeElement.appendChild(newDiv);

    // Create a new pre element
    var preCode = document.createElement("pre");

    // Append the codeElement to the preCode
    preCode.appendChild(codeElement);

    // Clear the inner HTML of node
    node.innerHTML = "";

    // Append the preCode to node
    node.appendChild(preCode);

    // Add the "code" class back to node
    node.className = "code";
  }
}


