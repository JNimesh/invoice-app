import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import HomePage from "../pages/home";
import InvoicesPage from "../pages/invoices";

export const Routes = () => {
  return (
    <Router>
      <Switch>
        <Route path="/invoices">
          <InvoicesPage />
        </Route>
        <Route path="/">
          <HomePage />
        </Route>
      </Switch>
    </Router>
  );
};
