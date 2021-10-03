import { useEffect, useState } from "react";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TablePagination from "@mui/material/TablePagination";
import TableRow from "@mui/material/TableRow";

import { withBasicLayout } from "../../components/layout";
import { getInvoices, updateInvoice } from "../../services/invoices";
import { formatDateAndTime } from "../../helpers/formatter";
import Filters from "../../components/invoices/filters";
import TableSortLabel from "@mui/material/TableSortLabel";
import Button from "@mui/material/Button";
import EmptyRecords from "../../components/table/emptyRecords";
import TableDataLoader from "../../components/table/tableDataLoader";

const COLUMNS = [
  { id: "id", label: "Invoice Number" },
  { id: "created", label: "Date", format: formatDateAndTime },
  { id: "customerName", label: "Customer Name" },
  { id: "grossAmount", label: "Gross Amount" },
  { id: "netAmount", label: "Net Amount" },
  { id: "discount", label: "Discount" },
  {
    id: "status",
    label: "Status",
    render: (invoice, onActionComplete) => {
      return (
        <StatusCellContent
          invoice={invoice}
          onApprovalComplete={onActionComplete}
        />
      );
    }
  }
];

const StatusCellContent = ({ onApprovalComplete, invoice }) => {
  const [updating, setUpdating] = useState(false);

  const handleClick = async () => {
    setUpdating(true);
    const updated = await updateInvoice({ ...invoice, status: "APPROVED" });
    setUpdating(false);
    onApprovalComplete(updated);
  };
  const { status } = invoice;

  return (
    <div>
      <div>{status}</div>
      {status === "PENDING" && (
        <Button
          variant="contained"
          onClick={() => handleClick()}
          disabled={updating}
        >
          {updating ? "Approving...." : "Approve"}
        </Button>
      )}
    </div>
  );
};

const ROWS_PER_PAGE = [5, 10, 25];

const InvoicesPage = () => {
  const [page, setPage] = useState(0);
  const [status, setStatus] = useState();
  const [pageSize, setPageSize] = useState(10);
  const [records, setRecords] = useState({});
  const [sortBy, setSortBy] = useState("id");
  const [sortOrder, setSortOrder] = useState("ASC");
  const [loading, setLoading] = useState(false);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = event => {
    setPageSize(+event.target.value);
    setPage(0);
  };

  const handleSortCriteria = sortColumnId => {
    if (sortBy === sortColumnId) {
      setSortOrder(sortOrder === "ASC" ? "DESC" : "ASC");
      setPage(0);
    } else {
      setSortBy(sortColumnId);
      setSortOrder("ASC");
      setPage(0);
    }
  };

  const handleFilters = value => {
    setStatus(value);
    setPage(0);
  };

  const onActionComplete = invoice => {
    const { content } = records;
    if ((content || []).length > 0) {
      const index = content.findIndex(record => record.id === invoice.id);
      if (index > -1) {
        const updatedContent = [
          ...content.slice(0, index),
          invoice,
          ...content.slice(index + 1)
        ];
        setRecords({ ...records, content: updatedContent });
      }
    }
  };

  useEffect(() => {
    async function getData() {
      setLoading(true);
      const data = await getInvoices({
        pageSize,
        page,
        status,
        sortBy,
        sortOrder
      });
      setLoading(false);
      setRecords(data);
    }

    getData();
  }, [pageSize, page, status, sortBy, sortOrder]);

  return (
    <>
      <Filters onStatusChange={value => handleFilters(value)} />
      <Paper sx={{ width: "100%", overflow: "hidden" }}>
        {loading ? (
          <TableDataLoader />
        ) : !records.totalElements ? (
          <EmptyRecords />
        ) : (
          <>
            <TableContainer>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    {COLUMNS.map(column => (
                      <TableCell
                        key={column.id}
                        align={column.align}
                        style={{ minWidth: column.minWidth }}
                      >
                        <TableSortLabel
                          active={sortBy === column.id}
                          direction={
                            sortBy === column.id
                              ? sortOrder.toLowerCase()
                              : "asc"
                          }
                          onClick={() => {
                            handleSortCriteria(column.id);
                          }}
                        >
                          {column.label}
                        </TableSortLabel>
                      </TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {records.content?.map(row => {
                    return (
                      <TableRow hover key={row.id}>
                        {COLUMNS.map(column => {
                          const value = row[column.id];
                          return (
                            <TableCell key={column.id} align={column.align}>
                              {column.render
                                ? column.render(row, onActionComplete)
                                : column.format
                                ? column.format(value)
                                : value}
                            </TableCell>
                          );
                        })}
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={ROWS_PER_PAGE}
              component="div"
              count={records?.totalElements}
              rowsPerPage={pageSize}
              page={page}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </>
        )}
      </Paper>
    </>
  );
};

export default withBasicLayout(InvoicesPage, { pageName: "Invoices" });
