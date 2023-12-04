import React, { createContext, useState } from "react";

// Context 생성
const HWContext = createContext();

// const HWProvider = ({ children }) => {
//   const [HWValue, setHWValue] = useState({
//     location: {
//       continent: "Asia",
//       country: "Korea",
//       region: null,
//     },
//     psf: 1.0,
//     pue: 1.0,
//     memory: 64,
//     type: "cpu",
//     cpu: { core: 12, model: "A8-7680", usage: 1.0, tdp: null },
//     gpu: { core: 1, model: "NVIDIA Jetson AGX Xavier", usage: 1.0, tdp: null },
//   });

//   return (
//     <HWContext.Provider value={{ HWValue, setHWValue }}>
//       {children}
//     </HWContext.Provider>
//   );
// };

const HWProvider = ({ children }) => {
  const [HWValue, setHWValue] = useState({
    hwSpecRequest: {
      cpuSpecRequest: {
        modelName: "Intel Core i9-14900K",
        usageFactor: 0.8,
        coreNumber: 8,
        tdp: null,
      },
      gpuSpecRequest: {
        modelName: "WRadeon 520",
        usageFactor: 0.7,
        coreNumber: 7,
        tdp: null,
      },
      memoryGigaByte: 8,
      psf: 2.7,
    },
    locationRequest: {
      continent: "NORTH_AMERICA",
      country: "Canada",
      region: "Nunavut",
    },
    javaCode: "abc"
  });

  return (
    <HWContext.Provider value={{ HWValue, setHWValue }}>
      {children}
    </HWContext.Provider>
  );
};

export { HWContext, HWProvider };
