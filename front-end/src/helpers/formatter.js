export const formatDateAndTime = dateStr => {
  const date = new Date(dateStr);
  return `${date.toDateString()} ${date.toLocaleTimeString()}`;
};
