const nodePositions = [
  ['14%', '22%'],
  ['32%', '64%'],
  ['52%', '34%'],
  ['70%', '72%'],
  ['86%', '28%'],
];

export default function GridlineAnimation({ variant = 'light' }) {
  return (
    <div className={`gridlineAnimation gridlineAnimation-${variant}`} aria-hidden="true">
      <div className="gridlinePlane" />
      <div className="gridlineSweep gridlineSweepX" />
      <div className="gridlineSweep gridlineSweepY" />
      <div className="gridlineTrace gridlineTraceOne" />
      <div className="gridlineTrace gridlineTraceTwo" />
      <div className="gridlinePanel gridlinePanelOne" />
      <div className="gridlinePanel gridlinePanelTwo" />
      {nodePositions.map(([left, top], index) => (
        <span
          className="gridlineNode"
          style={{ '--node-left': left, '--node-top': top, '--node-delay': `${index * .34}s` }}
          key={`${left}-${top}`}
        />
      ))}
    </div>
  );
}
