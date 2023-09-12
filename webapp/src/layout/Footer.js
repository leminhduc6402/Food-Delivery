import { Container } from "react-bootstrap";

const Footer = () => {
  return (
    <>
      <footer className="bg-dark text-light py-3">
        <Container className="text-center">
          &copy; {new Date().getFullYear()} Nomin. All rights reserved.
        </Container>
      </footer>
    </>
  );
};
export default Footer;
