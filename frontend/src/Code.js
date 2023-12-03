import React from "react";
import "./index.css";
import "./Code.css";
import AceEditor from "react-ace";
import { useContext, useState, useEffect } from "react";
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-monokai";
import "ace-builds/src-noconflict/ext-language_tools";
import { HWContext, HWProvider } from "./Hardware";


function onChange(newValue) {
  console.log("change", newValue);
}

function APICall() {
  const { HWValue, setHWValue } = useContext(HWContext);
  useEffect(() => {
    const SendDataToServer = async () => {
      try {
        const sender = {
          HWValue
        };
        alert(sender);
        const response = await fetch("http://localhost:8080/green", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(sender),
        });

        if (!response.ok) {
          throw new Error(`HTTP error, status : ${response.status}`);
        }

        const responseData = await response.json();
        console.log("Response data:", responseData);
      } catch (error) {
        console.error("Fetching error: ", error);
      }
    };

    SendDataToServer();
  }, []);
}

function Innercomponent() {
  const [height, setHeight] = useState(500);
  let [fade, setFade] = useState("");
  const { HWValue, setHWValue } = useContext(HWContext);
  const [javaCode, setJavaCode] = useState(`public class Main {
  public static void main(String[] args) {
    System.out.println("Hello, world!");
  }
}`);
  const onClick = () => {
    setHeight(1000);
  };

  const BtnClick = () => {
    setHWValue({
      ...HWValue,
      javaCode:javaCode,
    });

    APICall();
  };

  const onClear=()=> {
    setJavaCode(`public class Main {
  public static void main(String[] args) {
    System.out.println("Hello!");
  }
}`);
    setHeight(500);
  }

  useEffect(() => {
    setTimeout(()=> {
      setFade("end");
    }, 300);
    return setFade("");
  }, [height]);

  return (
    <div className="w-full">
      <div className={'code_area start '} onClick={onClick}>
        
        <AceEditor
          height={height}
          width='100%'
          placeholder="Placeholder Text"
          mode="java"
          theme="monokai"
          name="blah2"

          onChange={onChange}
          // onLoad={this.onLoad}
          // onChange={this.onChange}
          fontSize={24}
          showPrintMargin={true}
          showGutter={true}
          highlightActiveLine={true}
          value={javaCode}
          
          setOptions={{
            enableBasicAutocompletion: false,
            enableLiveAutocompletion: false,
            enableSnippets: false,
            showLineNumbers: true,
            tabSize: 2,
          }}
          
        />
      </div>
      <div style={{display: 'flex', justifyContent: "right", gap:10, padding:10}}>
        <button className="rounded bg-blue-500 px-4 py-2 font-bold text-white hover:bg-blue-600" >Upload</button>
        <button className="rounded bg-red-500 px-4 py-2 font-bold text-white hover:bg-red-600" onClick={onClear}>Clear</button>
        <button className="rounded bg-green-500 px-4 py-2 font-bold text-white hover:bg-green-600" onClick={BtnClick}>Submit</button>
      </div>
    </div>
  );
}

function Code() {
  return (
    <HWProvider>
      <Innercomponent />
    </HWProvider>
  );
}

export default Code;
