import React from "react";
import "./index.css";
import "./Code.css";
import AceEditor from "react-ace";
import Stat from "./Stat";
import { useContext, useState, useEffect } from "react";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";

import { CarbonContext, CarbonProvider } from "./Carbon";
import { HWContext, HWProvider } from "./Hardware";
import axios from 'axios';

function Innercomponent() {
  const [height, setHeight] = useState(500);
  const { HWValue, setHWValue } = useContext(HWContext);
  const { carbonValue, setCarbonValue } = useContext(CarbonContext);
  const [javaCodeValue, setJavaCodeValue] = useState(`public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}`);

  const CarbonList = (sender) => {
    axios.post("http://ec2-3-35-3-126.ap-northeast-2.compute.amazonaws.com:8080/green", sender)
      .then((res => {
        setCarbonValue(res.data);
      }))
      .catch((e) => {
        console.log(e);
      })
  }

  function onChange(newValue) {

    setJavaCodeValue(newValue);

  }

  useEffect(() => {
    setHWValue({
      ...HWValue,
      javaCode: javaCodeValue,
    });
  }, [javaCodeValue]);

  useEffect(() => {
    console.log(carbonValue);
  }, [carbonValue]);

  const onClick = () => {
    setHeight(1000);
  };

  const BtnClick = () => {
    const isKorean = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/.test(javaCodeValue);

    if (isKorean) {
      alert("Korean is not allowed\nPlease remove Korean.")
    }
    else {
      const sender = HWValue;
      console.log(sender);
      CarbonList(sender);
    }

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
            tabSize: 4,
          }}

        />
      </div>
      <div style={{ display: 'flex', justifyContent: "right", gap: 10, padding: 10 }}>
        <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" >Upload</button>
        <button className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600" onClick={onClear}>Clear</button>
        <button className="rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-600" onClick={BtnClick}>Submit</button>
      </div>

    </div>
  );
}

function Code() {
  return (
    <Innercomponent />
  );
}

export default Code;
