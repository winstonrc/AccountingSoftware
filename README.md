<div id="top"></div>

<!-- PROJECT SHIELDS -->
<!--
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Stars][stars-shield]][stars-url]
[![Forks][forks-shield]][forks-url]
[![Issues][issues-shield]][issues-url]
[![Contributors][contributors-shield]][contributors-url]
[![MIT License][license-shield]][license-url]
<!-- [![LinkedIn][linkedin-shield]][linkedin-url] -->



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/winstoncooke/AccountingSoftware">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">AccountingSoftware</h3>

  <p align="center">
    <a href="https://github.com/winstoncooke/AccountingSoftware/issues">Report Bug / Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

<img src="https://github.com/winstoncooke/AccountingSoftware/blob/main/images/screenshot.png" width=45% height=45%>

This program is designed to be a simple terminal/backend bookkeeping software. Currently, it allows for accounts to be created and added to a master Chart of Accounts. The accounts can have their balances updated using double entry accounting methods. The aim for the project is to be a lightweight, open-source, double-entry bookkeeping program for small organizations to utilize in lieue of more frustrating software such as QuickBooks.

Any feedback in making this program a more viable tool is welcomed and would be greatly appreciated! Thanks for checking it out.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [Java](https://www.java.com/)
* [SQLite](https://sqlite.org/)
* [JUnit 5](https://junit.org/junit5/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running, perform the following steps:

### Prerequisites

- Java 17
- SQLite

### Installation

1. Download the [latest release](https://github.com/winstoncooke/AccountingSoftware/releases)

2. Run the program
   ```sh
   java -jar AccountingSoftware-1.0-SNAPSHOT.jar
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

The program allows for the following:
- View the Chart of Accounts, which lists all created accounts and their respective balances, listed by account number
- Create an Asset, Liability, Equity, Revenue, or Expense account
- Input a double-entry transaction to update account balances
- Remove an account (with a balance of zero)

Safeguards have been put in place to prevent the books from accidentally becoming unbalanced. This includes only allowing a matching balance to be added to each account in the double entry input as well as preventing accounts from being deleted while still carrying a balance.

In the future, I aim to add multi-line entry functionality to enable partial amounts on one side of the transaction that sum to the total amount on the other side to be entered. Currently, the best way to achieve this would be to initially debit and credit the relevant accounts and then debit and credit the additional accounts against the original accounts until the balances are appropriate.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [ ] Add multi-line entry functionality
- [ ] Add GUI

See the [open issues](https://github.com/winstoncooke/AccountingSoftware/issues?q=is:open+is:issue) section for a full list of proposed features and known issues.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thank you!

1. Fork the Project
2. Create your Feature Branch: `git checkout -b feature/NewFeature`
3. Commit your Changes: `git commit -m 'Add some NewFeature'`
4. Push to the Branch: `git push origin feature/NewFeature`
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Winston Cooke - Please message me via my email address on my [profile](https://github.com/winstoncooke)

Project Link: [https://github.com/winstoncooke/AccountingSoftware](https://github.com/winstoncooke/AccountingSoftware)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
<!-- 
## Acknowledgments

* []()

<p align="right">(<a href="#top">back to top</a>)</p>
-->



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/winstoncooke/AccountingSoftware.svg?style=for-the-badge
[contributors-url]: https://github.com/winstoncooke/AccountingSoftware/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/winstoncooke/AccountingSoftware.svg?style=for-the-badge
[forks-url]: https://github.com/winstoncooke/AccountingSoftware/network/members
[stars-shield]: https://img.shields.io/github/stars/winstoncooke/AccountingSoftware.svg?style=for-the-badge
[stars-url]: https://github.com/winstoncooke/AccountingSoftware/stargazers
[issues-shield]: https://img.shields.io/github/issues/winstoncooke/AccountingSoftware.svg?style=for-the-badge
[issues-url]: https://github.com/winstoncooke/AccountingSoftware/issues
[license-shield]: https://img.shields.io/github/license/winstoncooke/AccountingSoftware.svg?style=for-the-badge
[license-url]: https://github.com/winstoncooke/AccountingSoftware/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png

<!-- Credit for README template: https://github.com/othneildrew/Best-README-Template -->
