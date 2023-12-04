import React, { createContext, useState } from "react";

// Context 생성
const CarbonContext = createContext();


const CarbonProvider = ({ children }) => {
    const [carbonValue, setCarbonValue] = useState({
        javaCode: null
    });

    return (
        <CarbonContext.Provider value={{ carbonValue, setCarbonValue }}>
            {children}
        </CarbonContext.Provider>
    );
};

export { carbonValue, setCarbonValue };
