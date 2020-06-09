# Neural-Networks-with-Neuroph-Library
Learning the data in the dataset we have chosen, with the Neuroph library and finding the error rates.

1. DEVELOPED SOFTWARE

First of all, I started with examining the working structure of artificial neural networks. Generally, the neuron in a particular layer of the network sends an output signal to other neurons in the next layer. So the output of the neuron in the previous layer becomes the input signal for the neuron in the next layer. The signals advance in this way and calculate an output in the output layer of the network.
 Back propagation, on the other hand, when an epoch is finished, that is reading the entire data set, the program calculates the network error and changes the weights between neurons in the network. It advances this by starting the output layer and working backwards by adjusting the weights of each neuron connection. The reason for using momentum here is to keep the algorithm in the previous direction, avoiding the local minimum. With momentum, when weights start moving in a certain direction, they tend to continue moving in that direction. As a sampling, when a ball rolling up and down the hill is stuck in a depression, it continues its movement if it has enough momentum.
When training the a data set(egitim.txt), training values feeds neural network an calculate an output. Then compare it with the expected output and with this calculation I have the output error for neural network. And then, by adjusting all the neuron connection weights in the network layers, it spreads the error backwards and sends the training data again. This is iteration, how many times it will be wrong depends on the number of epoch. 

Here I use the sigmoid function, because of that I had to normalize the data to min-max normalization.

This process continues to a point that accepts that the network is trained, that is, until the level of fault tolerance is achieved. I called it min error in the source code.

There are many signals coming to an artificial neuron, and the network must convert them into a single value that can evaluate them. The most commonly used here is the weighted aggregation, that is, the input value of each link is multiplied by the weight of that link and that all the signals connected to that neuron are summed.

       n 
    an=∑(ai*wi)     
      i=1
      
 The output, the activation function, determines if it passes the output signal to all neurons in the next layer. I used the Sigmoid function in this application. Because sigmoid is not linear. The description of the equation grows in the numerator for the increasingly large negative values of A and the value of the function approaches zero. As the positive values of A grow larger, the exponential term disappears and its value approaches 1.
 
The most common error function in multi-layered networks is ms. Basically what it does is to calculate the average distance between the output value calculated in the training and the expected output.

            n 
    E=(1/n)*∑(expected-output)^2              
           I=1  
          
  In each iteration, it calculates how far its output is from expectation and adjusts the network weights accordingly. The size of the correction is proportional to the error on the network.
  
  RESULTS
  
   I used NeurophStudio to make different network designs and display the received errors in a table. NeurophStudio started a new project and created a new neural network. Here, I chose MultiLayerPerceptron, input 5, output 1 information according to our project, transferFunction sigmoid and learningRule as BackPropagation with momentum.
  Then I added a data set, this was the egitim.txt used in the dataset code. The data in this dataset should be between 0-1 since I used the sigmoid function. As I did in the code, I normalized this information with min-max, so they entered the range 0-1.

eg. Max-error = 0.01, epoch = 1000, learning coefficient = 0.5, momentum = 0.7, layer = 15

![java](https://user-images.githubusercontent.com/47714688/84189894-ab142400-aa9e-11ea-8041-a146ab2237c7.png)
