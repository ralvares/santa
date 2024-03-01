class CPE
  attr_accessor :part
  attr_accessor :vendor
  attr_accessor :product
  attr_accessor :version
  attr_accessor :update
  attr_accessor :edition
  attr_accessor :language

  def initialize(args = {})
    self.part = args[:part]
    self.vendor = args[:vendor] || ""
    self.product = args[:product] || ""
    self.version = args[:version] || ""
    self.update = args[:update] || ""
    self.edition = args[:edition] || ""
    self.language = args[:language] || ""
  end

  def self.parse(cpe)
    raise ArgumentError, "Invalid input. Expected a File or String." unless cpe.is_a?(File) || cpe.is_a?(String)

    cpe = cpe.read if cpe.is_a?(File)
    cpe = cpe.to_s.downcase.strip

    unless /^cpe:\d+(\.\d+)?:[aho]:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+:[a-zA-Z0-9._\-:*]+$/.match(cpe)
      raise ArgumentError, "CPE malformed"
    end


    discard, part, vendor, product, version, update, edition, language = cpe.split(/:/, 8)

    new(
      part: part,
      vendor: vendor,
      product: product,
      version: version,
      update: update,
      edition: edition,
      language: language
    )
  end
end

def parse_and_print_cpe_entries(cpe_list)
  cpe_list.each do |cpe_entry|
    begin
      parsed_cpe = CPE.parse(cpe_entry)

      puts "Part: #{parsed_cpe.part}"
      puts "Vendor: #{parsed_cpe.vendor}"
      puts "Product: #{parsed_cpe.product}"
      puts "Version: #{parsed_cpe.version}"
      puts "Update: #{parsed_cpe.update}"
      puts "Edition: #{parsed_cpe.edition}"
      puts "Language: #{parsed_cpe.language}"
      puts "---------------------------" # Separate each entry for clarity
    rescue ArgumentError => e
      puts "Error: #{e.message}"
      puts "---------------------------" # Separate each entry for clarity
    end
  end
end

# Example CPE entries to test the parser
cpe_entries = [
  "cpe:2.3:a:apache:log4j-core:2.14.1:*:*:*:*:*:*:*",
  "cpe:2.3:a:apache:log4j_core:2.14.1:*:*:*:*:*:*:*",
  "cpe:2.3:a:apache:logging:2.14.1:*:*:*:*:*:*:*",
  "cpe:2.3:a:apache:log4j:2.14.1:*:*:*:*:*:*:*"
]

parse_and_print_cpe_entries(cpe_entries)
