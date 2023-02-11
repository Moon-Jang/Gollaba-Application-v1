import React, { useState, useEffect } from "react";
import Poll from "./poll";

const PollsMap = (props) => {
  const data = props.data;
  return data.map((el) => <Poll data={el} />);
};

export default PollsMap;
