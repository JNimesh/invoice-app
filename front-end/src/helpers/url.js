export const getQueryParams = params => {
  if (params) {
    const queryString = Object.keys(params)
      .filter(key => params[key] !== undefined)
      .reduce(
        (paramsString, key) =>
          `${paramsString}${paramsString ? "&" : ""}${key}=${params[key]}`,
        ""
      );
    if (queryString) {
      return `?${queryString}`;
    }
    return "";
  }
  return "";
};
