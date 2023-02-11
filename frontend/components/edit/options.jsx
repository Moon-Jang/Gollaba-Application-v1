import React, { useState, useEffect } from "react";
import Option from "./option";

const Options = (props) => {
  const pollingItems = props.pollingItems;
  const setPollingItems = props.setPollingItems;
  pollingItems.sort((a, b) => a.sequence - b.sequence);
  return pollingItems.map((el) => (
    <Option
      pollingItems={el}
      entireItems={pollingItems}
      setPollingItems={setPollingItems}
    />
  ));
};

export default Options;
