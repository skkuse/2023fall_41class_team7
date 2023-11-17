import "./index.css";
import "./info.css";
function Info() {
  return (
    <div class="flex justify-center min-h-screen pt-10 info_area">
      <div class="flex flex-col w-full max-w-7xl">
        <div class="text-3xl font-extrabold ml-10 mb-10 text-gray-800 text-center">Service Information</div>
        <div class="container p-4 m-4 bg-blue-100 shadow-lg rounded">
          <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Problem : CO2</h2>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">As carbon emissions continue to increase due to various human activities,</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">carbon dioxide concentrations increase, accelerating global warming.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">This causes extreme climate events such as droughts more frequently around the world,</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">raising average temperatures, and severely affecting the global ecosystem.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">We need to be aware of the seriousness of carbon emissions to the global ecosystem.</p>
        </div>

        <div class="flex justify-evenly">
        <div class="flex-grow">
          <div class="container p-4 m-4 bg-green-100 shadow-lg rounded">
            <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">What is Green Software?</h2>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">It refers to software that is developed and operated in an environmentally friendly way.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">Energy efficiency, reusability, recyclability, and sustainability are considered important factors.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">It aims to minimize power consumption and reduce its long-term impact on the environment.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">It can be achieved through methods such as code optimization and energy-efficient algorithm use.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">Reduce unnecessary SW power consumption for the planet!</p>
          </div>
        </div>
        <div class="flex-grow ml-4">
          <div class="container p-4 m-4 bg-pink-100 shadow-lg rounded">
            <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Services provided</h2>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">Calculate the carbon emissions of the user's Java source code and provide the results.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">The result value allows the user to work on code optimization,</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">and continuously check carbon emissions in the process.</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">The calculation results are replaced not only by figures,</p>
            <p class="text-2xl text-gray-700 font-bold mb-2 text-center">but also by carbon emissions from various means of transportation, making them more visually visible.</p>
          </div>
        </div>
      </div>

        <div class="container p-4 m-4 bg-yellow-100 shadow-lg rounded">
          <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Carbon Emissions Formula</h2>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">carbon footprint = energy needed * carbon intensity</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">runtime * (power draw for cores * usage + power draw for memory) * PUE * PSF</p>
        </div>

        <div class="container p-4 m-4 bg-indigo-100 shadow-lg rounded">
          <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Input method</h2>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">Input method</p>
        </div>

        <div class="container p-4 m-4 bg-red-100 shadow-lg rounded text-center">
          <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Code / More Information</h2>
          <a href="https://github.com/skkuse/2023fall_41class_team7" target="_blank" rel="noopener noreferrer" 
          class="text-blue-500 text-2xl underline font-bold">
            GitHub
          </a>
        </div>

      </div>
    </div>
  );
}

export default Info;
