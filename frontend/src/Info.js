import "./index.css";
import "./info.css";
function Info() {
  return (
    <div class="flex justify-center min-h-screen pt-10 info_area">
      <div class="flex flex-col w-full max-w-7xl">
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
            <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Services Provided</h2>
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
          <p class="text-2xl text-red-500 font-bold mb-2 text-center">carbon footprint = energy needed * carbon intensity</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 text-center">Where the energy needed is:</p>
          <p class="text-2xl text-red-500 font-bold mb-2 text-center">runtime * (power draw for cores * usage + power draw for memory) * PUE * PSF</p>
          <p class="text-1xl text-gray-700 font-bold mb-2 text-center">The power draw for the computing cores depends on the model and number of cores.</p>
          <p class="text-1xl text-gray-700 font-bold mb-2 text-center">The memory power draw only depends on the size of memory available and the usage factor corrects for the real core usage.</p>
          <p class="text-1xl text-gray-700 font-bold mb-2 text-center">The PUE (Power Usage Effectiveness) measures how much extra energy is needed to operate the data center.</p>
          <p class="text-1xl text-gray-700 font-bold mb-2 text-center">The PSF (Pragmatic Scaling Factor) is used to take into account multiple identical runs.</p>
        </div>

        <div class="container p-4 m-4 bg-indigo-100 shadow-lg rounded ">
          <h2 class="text-3xl font-extrabold mb-2 text-black-600 text-center">Input Method</h2>
          <p class="text-3xl text-red-600 font-bold mb-2 text-center">Users can configure their hardware environment in the 'server' sector.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>Type of cores</span>: CPU, GPU, both options are available.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>number of cores</span>: Users can choose the number of cores.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>usage</span>: The ratio at which the processor operates at maximum performance. The default value is 1, and users can set a decimal value between 0 and 1 if necessary.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>model</span>: The type of model used for calculations, used to calculate Thermal Design Power (TDP) per core.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>memory</span>: The available memory, measured in GB.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>pue(Power Usage Effectiveness)</span>: Reflects the data center's power usage efficiency, the ratio of total energy consumption to the energy supplied to IT equipment. The default value is 1. </p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>psf(Power Scaling Factor)</span>: A power scaling coefficient used to adjust the system's power usage. The default value is 1, and users can adjust it if necessary.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>location(Location)</span>: The value necessary for calculating carbon intensity, indicating how much carbon the electricity in the corresponding country emits.</p>
          <p class="text-3xl text-red-600 font-bold mb-2 text-center">In the 'Home' sector, users can input Java code.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>Clear button</span>: Users can reset the written Java code.</p>
          <p class="text-2xl text-gray-700 font-bold mb-2 ml-2"><span style={{color: 'green'}}>Submit button</span>: Users can save their code and hardware specifications in JSON format to interact with the backend and check the calculated carbon emissions.</p>
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
