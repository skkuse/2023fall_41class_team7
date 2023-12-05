import React from "react";
import "./index.css";
import "./Code.css";
import AceEditor from "react-ace";
import Stat from "./Stat";
import { useContext, useState, useEffect } from "react";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";
// import { CarbonProvider, useData } from "./Carbon";
import { CarbonContext, CarbonProvider } from "./Carbon";
import { HWContext, HWProvider } from "./Hardware";
import axios from 'axios';





function Innercomponent() {
  const [data, setData] = useState(null);
  const [height, setHeight] = useState(500);
  const { HWValue, setHWValue } = useContext(HWContext);
  // const { setCarbonValue } = useData();
  const { carbonValue, setCarbonValue } = useContext(CarbonContext);
  const [javaCodeValue, setJavaCodeValue] = useState(`public class Main {
    public static void main(String[] args) {
      System.out.println("Hello, world!");
    }
}`);

  const CarbonList = (sender) => {
    axios.post("http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/green", sender)
      .then((res => {

        setData(res.data);

      }))
      .catch((e) => {
        console.log(e);
      })
  }

  function onChange(newValue) {
    setJavaCodeValue(newValue);
  }


  useEffect(() => {
    // console.log(data);
    // 바꿔주기
    setCarbonValue(data);
    console.log(carbonValue);

  }, [data]);

  useEffect(() => {
    setHWValue({
      ...HWValue,
      javaCode: javaCodeValue,
    });
  }, [javaCodeValue]);


  const onClick = () => {
    setHeight(1000);
  };

  const BtnClick = () => {
    const sender = HWValue;



    // API POST request

    CarbonList(sender);


    // try {
    //   const response = fetch("http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/green", {
    //     method: "POST",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(sender)


    //   }).then(res => {
    //     if (res.ok) {
    //       return res.json(); // JSON 파싱하여 반환
    //     }

    //   })
    //     .then(data => {
    //       console.log(data) //응답 데이터 확인
    //     })

    //     .catch(error => {
    //       console.error("error: ", error);
    //     });

    // } catch (error) {
    //   console.error("Fetching error: ", error);
    // };

  }

  const onClear = () => {
    setJavaCodeValue(`public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}`);
    setHeight(500);
  }


  return (
    <div className="w-full">
      <div className={'code_area start '} onClick={onClick}>

        <AceEditor
          height={height.toString() + "px"}
          width='100%'
          placeholder="Placeholder Text"
          mode="java"
          theme="monokai"
          name="blah2"
          onChange={onChange}
          // onLoad={this.onLoad}
          fontSize={20}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          value={javaCodeValue}

          setOptions={{
            enableBasicAutocompletion: false,
            enableLiveAutocompletion: false,
            enableSnippets: false,
            showLineNumbers: true,
            tabSize: 2,
          }}

        />
      </div>
      <div style={{ display: 'flex', justifyContent: "right", gap: 10, padding: 10 }}>
        <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" >Upload</button>
        <button className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600" onClick={onClear}>Clear</button>
        <button className="rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-600" onClick={BtnClick}>Submit</button>
      </div>
      <Stat />

    </div>
  );
}

function Code() {
  return (
    <CarbonProvider>
      <HWProvider>
        <Innercomponent />
      </HWProvider>
    </CarbonProvider>
  );
}

export default Code;
