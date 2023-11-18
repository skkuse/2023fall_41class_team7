import React, { createContext, useState } from "react";

// Context 생성
const HWContext = createContext();

const HWProvider = ({ children }) => {
  const [HWValue, setHWValue] = useState({
    location: {
      continent: "Asia",
      country: "Korea",
    },
    psf: 1.0,
    pue: 1.0,
    memory: 64,
    type: "cpu",
    cpu: { core: 12, model: "A8-7680", usage: 1.0, tdp: null },
    gpu: { core: 1, model: "NVIDIA Jetson AGX Xavier", usage: 1.0, tdp: null },
  });

  return (
    <HWContext.Provider value={{ HWValue, setHWValue }}>
      {children}
    </HWContext.Provider>
  );
};

export { HWContext, HWProvider };
