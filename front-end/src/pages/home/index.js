import { withBasicLayout } from "../../components/layout";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import ImageListItemBar from "@mui/material/ImageListItemBar";
import { Typography } from "@mui/material";
import InvoiceTile from "../../assets/invoices.png";
import { useHistory } from "react-router-dom";
import { Paths } from "../../routes/paths";

const itemData = [
  {
    img: InvoiceTile,
    title: "Invoices",
    url: Paths.invoices
  }
];

const HomePage = () => {
  const history = useHistory();

  return (
    <ImageList cols={4}>
      {itemData.map(item => (
        <ImageListItem
          onClick={() => history.push(item.url)}
          key={item.img}
          sx={{ border: 1 }}
          style={{ maxWidth: "800px", textAlign: "left", cursor: "pointer" }}
        >
          <img src={item.img} alt={item.title} loading="lazy" />
          <ImageListItemBar
            sx={{ p: "8px" }}
            title={<Typography variant="h4">{item.title}</Typography>}
            position="below"
          />
        </ImageListItem>
      ))}
    </ImageList>
  );
};

export default withBasicLayout(HomePage, { pageName: "HOME" });
