import React, { createContext, useContext, useState } from "react";

// Context 생성
const CarbonContext = createContext();

// export const CarbonProvider = ({ children }) => {
//     const [carbonValue, setCarbonValue] = useState(null);
//     const setCarbonDataValue = (data) => {
//         setCarbonValue(data);
//     };
//     return (
//         <CarbonContext.Provider value={{ carbonValue, setCarbonValue: setCarbonDataValue }}>
//             {children}
//         </CarbonContext.Provider>
//     );
// };

// export const useData = () => {
//     const context = useContext(CarbonContext);
//     if (!context) {
//         throw new Error('useData must be used within a DataProvider');
//     }
//     return context;
// };



const CarbonProvider = ({ children }) => {
    const [carbonValue, setCarbonValue] = useState({
        hwFootprint: {
            cpuCarbonFootprint: 0,
            gpuCarbonFootprint: 0,
            memoryCarbonFootprint: 0,
        },
        interpretedFootprint: {
            flightFromIncheonToTokyo: 0,
            passengerCar: 0,
            treeMonths: 0,
        },
        totalCarbonFootprint: 0,
        totalEnergyNeeded: 0
    });

    return (
        <CarbonContext.Provider value={{ carbonValue, setCarbonValue }}>
            {children}
        </CarbonContext.Provider>
    );
};

export { CarbonContext, CarbonProvider };
