import { Stats, Title } from '../components/Common';

export default function PlacementsPage() {
  const placements = [
    ['Aniket Dafal', '6.5 LPA', '1.Aniket Dafal - 6.5 LPA.jpg'],
    ['Rushikesh Birajdar', '6.5 LPA', '2. Rushikesh Birajdar - 6.5 LPA.jpg'],
    ['Shubham Chavan', '6.5 LPA', '3. Shubham Chavan - 6.5 LPA.jpg'],
    ['Akshay Parimal', '10 LPA', '4. Akshay Parimal - 10 LPA.jpg'],
    ['Baishakhee Routray', '4.66 LPA', '5. Baishakhee Routray - 4.66 LPA.jpg'],
    ['Darpan Nachane', '4 LPA', '6. Darpan Nachane - 4 LPA.jpg'],
    ['Kaustubh Patne', '12 LPA', '7. Kaustubh Patne - 12 LPA.jpg'],
    ['Narendra Yadav', '9 LPA', '8. Narendra Yadav - 9 LPA.jpg'],
    ['Pranav Mali', '9 LPA', '9. Pranav Mali - 9 LPA.jpg'],
    ['Rohit Gund', '8 LPA', '10. Rohit Gund - 8 LPA.jpg'],
    ['S R Pavan', '12 LPA', '11. S R Pavan - 12 LPA.jpg'],
    ['Somesh Tapkire', '8 LPA', '12. Somesh Tapkire - 8 LPA.jpg'],
  ];

  const parsePackageValue = (value) => parseFloat(value.split(' ')[0]);
  const sortedPlacements = [...placements].sort((a, b) => parsePackageValue(b[1]) - parsePackageValue(a[1]));

  return (
    <section className="page">
      <Title title="Verified Placements Analytics & Success Records" />
      <Stats />
      <div className="grid cards">
        {sortedPlacements.map(([name, packageAmount, photo]) => (
          <article key={name} className="card profile">
            <img
              className="profilePhoto"
              src={new URL(`../../images/${photo}`, import.meta.url).href}
              alt={name}
            />
            <h3>{name}</h3>
            <b>{packageAmount} Package Secured</b>
          </article>
        ))}
      </div>
    </section>
  );
}
