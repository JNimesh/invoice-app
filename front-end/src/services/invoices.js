import { getQueryParams } from "../helpers/url";

const { REACT_APP_BASE_API_URL } = process.env;
const INVOICES_API_BASE_URL = `${REACT_APP_BASE_API_URL ||
  "http://localhost:8080"}/invoices`;

export async function getInvoices({
  page,
  pageSize,
  sortBy,
  sortOrder,
  status
} = {}) {
  const queryParams = getQueryParams({
    page,
    pageSize,
    sortBy,
    sortOrder,
    status
  });
  const response = await fetch(`${INVOICES_API_BASE_URL}${queryParams}`, {
    mode: "cors"
  });
  return await response.json();
}

export async function updateInvoice(invoice) {
  const response = await fetch(`${INVOICES_API_BASE_URL}/${invoice.id}`, {
    method: "PUT",
    mode: "cors",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(invoice)
  });
  return await response.json();
}
