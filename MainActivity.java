private lateinit var timeZoneSpinner: Spinner

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Inisialisasi View
    timeTextView = findViewById(R.id.timeTextView)
    dateTextView = findViewById(R.id.dateTextView)
    timeZoneSpinner = findViewById(R.id.timeZoneSpinner)

    // Atur Spinner
    val timeZones = TimeZone.getAvailableIDs().toList()
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeZones)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    timeZoneSpinner.adapter = adapter

    timeZoneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedTimeZone = timeZones[position]
            updateTime(selectedTimeZone)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    updateDate()
}

private fun updateTime(timeZoneId: String = TimeZone.getDefault().id) {
    val timeZone = TimeZone.getTimeZone(timeZoneId)
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    timeFormat.timeZone = timeZone

    handler.post(object : Runnable {
        override fun run() {
            val currentTime = timeFormat.format(Date())
            timeTextView.text = currentTime
            handler.postDelayed(this, 1000) // Perbarui setiap detik
        }
    })
}
