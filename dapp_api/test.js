
let dayjs = require('dayjs')

process.env.TZ = 'Asia/Shanghai';
console.log(dayjs().format('YYYY-MM-DD HH:mm:ss'))
// let a = dayjs('2018-08-08 00:00:00');     // parse
// console.log(a)

// a = dayjs().format('YYYY MM-DD HH:mm:ss') // display
// console.log(a)

// a = dayjs().set('month', 3).month() // get & set
// console.log(a)

// a = dayjs().add(1, 'year') // manipulate
// console.log(a)





// do {
//     let offset = date.add(minute, 'minute')

//     let num = `${flag}`.padStart(4, '0');
//     let startTime = date.format('HH:mm:ss')
//     let endTime = offset.format('HH:mm:ss')
//     console.log(num + " - " + startTime + " - " + endTime);

//     date = offset;
//     flag+=1;
// } while (flag <= end);



let minute = 5;
let gameId = 1;
let end = 60 * 24 / minute;
let fixed = 4;
// let r = gen(gameId, end, fixed, minute)
// console.log(JSON.stringify(r))

(async ()=>{
    let commonHelper = require("./utils/commonUtil")
    // let rr = await commonHelper.genExpect(gameId, end, fixed, minute)
    // console.log(JSON.stringify(rr))
})()

