import ButtonAppBar from "./Header";
import PageContainer from "./PageContainer";

export function withBasicLayout(Component, { pageName }) {
  return function(props) {
    return (
      <>
        <ButtonAppBar pageName={pageName} />
        <PageContainer>
          <Component {...props} />
        </PageContainer>
      </>
    );
  };
}
