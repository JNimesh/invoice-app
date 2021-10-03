import Container from "@mui/material/Container";

export default function PageContainer({ children }) {
  return (
    <>
      <Container sx={{ p: "20px" }} maxWidth={false}>
        {children}
      </Container>
    </>
  );
}
