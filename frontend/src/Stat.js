import "./index.css";
import car from './car.png';
import plane from './plane.png';

function Stat() {

  return (<div class="flex flex-col">
  <div class="w-full h-32 bg-stat-orange flex items-center">
    <img src={car} alt="Your Image" class="mr-2 w-48 h-24"/>
    <div class="flex-grow bg-stat-orange h-16"></div> 
    <div class="flex items-center flex flex-col w-64">
      <div class="font-bold text-4xl text-black">1km</div>
      <div class="italic text-xl text-black">in a passenger car</div>
    </div>
  </div>
  <div class="w-full h-32 bg-stat-skyblue flex items-center">
    <img src={plane} alt="Your Image" class="mr-2 w-48 h-24"/>
    <div class="flex-grow bg-stat-skyblue h-16"></div> 
    <div class="flex items-center flex flex-col w-64">
      <div class="font-bold text-4xl text-black">1km</div>
      <div class="italic text-xl text-black">of a flight Paris-London</div>
    </div>
  </div>
  <div class="w-full h-32 bg-stat-grass"></div>
</div>);
  

}

export default Stat;
